package uk.co.platosys.fingerprinter.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;

/**
 * ReadTapp is the basic Activity for reading Vouchrs.
 *
 * Created by edward on 03/03/18.
 */

public class ReadTapp extends TappActivity {
    String tappID;

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i("TA", "onCreate called");
    setContentView(R.layout.activity_tapp);
    ButterKnife.bind(this);
    contentView.setText("");
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showAlert(R.string.sign_alert_header, R.string.sign_alert_message, signOKOnClickListener);
        }
    });
    fab.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            reply();
            return true;

        }
    });
}
    private DialogInterface.OnClickListener signOKOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //TODO call signing engine
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
                default:
                    dialog.dismiss();

            }
        }

    };
    private void reply(){
        Intent replyIntent = new Intent(this, CreateTapp.class);
        replyIntent.putExtra(FPConstants.TAPPID_INTENT_KEY, tappID);
        startActivity(replyIntent);
    }

}
