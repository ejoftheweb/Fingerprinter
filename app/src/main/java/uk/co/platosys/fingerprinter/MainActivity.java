package uk.co.platosys.fingerprinter;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
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
LockStore remoteLockStore;
LockStore deviceLockStore;
Lock theirLock;
Lock ourLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("URL", MODE_PRIVATE);
        try{
            URL url = new URL(FPConstants.HKP_PROTOCOL, FPConstants.HKP_HOST, FPConstants.HKP_PORT, FPConstants.HKP_FILE);
            this.lockStore = new HKPLockStore(url);
            this.deviceLockStore=new MinigmaLockStore()
            ourLock = lockStore.getLock()
        }catch (MalformedURLException murlx){

        }
        setContentView(R.layout.activity_main);
        emailAddress.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {
                                                    try {
                                                        theirLock = lockStore.getLock(editable.toString());
                                                    } catch (MinigmaException mex){
                                                        Exceptions.dump(mex);
                                                    }
                                                }
                                            }
        );
    }
}
