package uk.co.platosys.fingerprinter;

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
import uk.co.platosys.fingerprinter.exceptions.NullLockException;
import uk.co.platosys.minigma.HKPLockStore;
import uk.co.platosys.minigma.Lock;
import uk.co.platosys.minigma.LockStore;
import uk.co.platosys.minigma.MinigmaLockStore;
import uk.co.platosys.minigma.exceptions.Exceptions;
import uk.co.platosys.minigma.exceptions.MinigmaException;

public class MainActivity extends AppCompatActivity {
@BindView(R.id.emailaddress)
    EditText emailAddress;
@BindView(R.id.biomeslist)
    ListView biomesList;
@BindView(R.id.ourWord)
    TextView ourWordView;
@BindView(R.id.emailaddresslabel)
TextView emailAddressLabel;
@BindView(R.id.button)
    Button button;
LockStore remoteLockStore;
LockStore deviceLockStore;
Lock theirLock;
Lock ourLock;
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("URL", MODE_PRIVATE);
        this.username=sharedPreferences.getString(FPConstants.PREFS_USERNAME_KEY, FPConstants.PREFS_USERNAME_KEY);
        try{
            URL url = new URL(FPConstants.HKP_PROTOCOL, FPConstants.HKP_HOST, FPConstants.HKP_PORT, FPConstants.HKP_FILE);
            this.remoteLockStore = new HKPLockStore(url);
            this.deviceLockStore=new MinigmaLockStore(FPConstants.PGP_KEYRING_FILE, false);
            ourLock = deviceLockStore.getLock(username);
        }catch (MalformedURLException murlx){

        }catch (MinigmaException mex){

        }
        setContentView(R.layout.activity_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    theirLock = remoteLockStore.getLock(emailAddress.getText().toString());
                    start();
                } catch (Exception mex){
                    Exceptions.dump(mex);
                }
            }
        });
    }

    private void start() throws Exception{
        if (ourLock==null){ throw new NullLockException("start called with null lock");}
        if (theirLock==null){throw new NullLockException("their lock is null");}
            List<String> ourfingerprint = ourLock.getFingerprint().getFingerprint();
            List<String> theirfingerprint = theirLock.getFingerprint().getFingerprint();
            ourWordView.setText(ourfingerprint.get(0));
    }

}
