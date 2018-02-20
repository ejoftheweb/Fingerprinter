package uk.co.platosys.fingerprinter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.exceptions.NullLockException;
import uk.co.platosys.minigma.Fingerprint;
import uk.co.platosys.minigma.HKPLockStore;
import uk.co.platosys.minigma.Lock;
import uk.co.platosys.minigma.LockStore;
import uk.co.platosys.minigma.MinigmaLockStore;
import uk.co.platosys.minigma.exceptions.Exceptions;
import uk.co.platosys.minigma.exceptions.MinigmaException;

public class MainActivity extends AppCompatActivity {


        @BindView(R.id.emailaddress)
        EditText emailAddress;
        @BindView(R.id.ourWord)
        TextView ourWordView;
        @BindView(R.id.theirWord0)
        TextView theirWord0;
        @BindView(R.id.theirWord1)
        TextView theirWord1;
        @BindView(R.id.theirWord2)
        TextView theirWord2;
        @BindView(R.id.theirWord3)
        TextView theirWord3;
        @BindView(R.id.button)
        Button button;
        LockStore remoteLockStore;
        LockStore deviceLockStore;
        Lock theirLock;
        Lock ourLock;
        List<String> ourfingerprint;
        List<String> theirfingerprint;
        int position=-1;
        int index=0;
        String targetWord="";
        String username;
        boolean pass=false;
        int size=0;
    TextView[] theirWords ={theirWord0,theirWord1,theirWord2,theirWord3};

    private View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                if (textView.getText().equals(targetWord)){
                    pass=true;
                    if (index < theirfingerprint.size()) {
                        index++;
                        increment();
                    } else {
                        success();
                    }


                }else{
                    pass=false;
                }
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("URL", MODE_PRIVATE);
        try {
            this.username = sharedPreferences.getString(FPConstants.PREFS_USERNAME_KEY, FPConstants.PREFS_USERNAME_KEY);
        }catch(Exception x){
            Exceptions.dump(x);
        }
        if (username.equals(FPConstants.PREFS_USERNAME_KEY)){
            Intent signUpIntent = new Intent(this, SignupActivity.class);
            startActivity(signUpIntent);
        }else {
            try {
                this.remoteLockStore = new HKPLockStore(FPConstants.HKP_HOST, FPConstants.HKP_PORT);
                this.deviceLockStore = new MinigmaLockStore(FPConstants.PGP_KEYRING_FILE, false);
                ourLock = deviceLockStore.getLock(username);

            } catch (MinigmaException mex) {
                Exceptions.dump(mex);
            }
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            button = findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        theirLock = remoteLockStore.getLock(emailAddress.getText().toString());
                        theirfingerprint = theirLock.getFingerprint().getFingerprint();
                        start();
                    } catch (Exception mex) {
                        Exceptions.dump(mex);
                    }
                }
            });
            TextView[] theirWords = {theirWord0, theirWord1, theirWord2, theirWord3};

            for (TextView textView : theirWords) {
                textView.setOnClickListener(clickListener);
            }
        }
    }

    private void start() throws Exception {
        if (ourLock == null) {
            throw new NullLockException("start called with null lock");
        }
        if (theirLock == null) {
            throw new NullLockException("their lock is null");
        }
        ourfingerprint = ourLock.getFingerprint().getFingerprint();
        increment();
    }

    private void fillTable(int index){
        position = (int) Math.rint(Math.random()*4);
        targetWord = theirfingerprint.get(index);
        theirWords[position].setText(targetWord);
        for (int i=0; i<theirWords.length; i++){
            if (i!=position){
                String fillword="";
                while(!fillword.equals(targetWord)) {
                    fillword = Fingerprint.EVEN_BIOMES[(int) Math.rint(Math.random() * Fingerprint.EVEN_BIOMES.length)];
                }
                theirWords[i].setText(fillword);
            }
        }
    }

    private void increment() {
        ourWordView.setText(ourfingerprint.get(index));
        fillTable(index);

    }
    public void success (){

    }


}
