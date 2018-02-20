package uk.co.platosys.fingerprinter;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.minigma.exceptions.Exceptions;

public class SignupActivity extends AppCompatActivity {
@BindView(R.id.namelabel)
    TextView nameLabelView;
    @BindView(R.id.name)
    EditText nameView;
    @BindView(R.id.emailaddresslabel)
    TextView emailLabelView;
    @BindView(R.id.emailaddress)
    EditText emailView;
    @BindView(R.id.button)
    Button button;
    String name;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    name=nameView.getText().toString();
                    email=emailView.getText().toString();
                    Log.i("SUA", "name="+name+", email="+email);
                    if (isExternalStorageWritable()) {
                        /*File lockStoreFolder = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FPConstants.PGP_PUBRING_FOLDER);
                        while (!lockStoreFolder.exists()&&lockStoreFolder.canWrite()) {
                            lockStoreFolder.mkdir();

                        }
                            File lockStoreFile = new File(lockStoreFolder, FPConstants.PGP_PUBRING_FILENAME);*/
                       /* LockStore lockStore = new MinigmaLockStore(lockStoreFile, true);
                        File keyFolder = getFilesDir();
                        File userFolder = new File (keyFolder, name);
                        if(!userFolder.exists()){userFolder.mkdir();}
                        if (userFolder.isDirectory()){
                            Log.i("SUA", userFolder.getAbsolutePath()+" is directory");
                        }else{
                            Log.i("SUA", userFolder.getAbsolutePath()+" is not a directory");
                        }


                        Lock lock  = LockSmith.createLockset(userFolder, lockStore,email,passphrase, Algorithms.RSA);*/
                        selectPassphrase(name, email);

                    }
                }catch (Exception mex){
                    Exceptions.dump(mex);
                }
            }
        });
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    private void selectPassphrase(String name, String email){
        Intent intent = new Intent(this, Choose_Passphrase.class);
        intent.putExtra("name",name);
        intent.putExtra( "email", email);
        startActivity(intent);

    }
}
