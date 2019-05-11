package uk.co.platosys.fingerprinter.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.fingerprinter.models.user.VouchUser;
import uk.co.platosys.fingerprinter.services.VouchService;
import uk.co.platosys.minigma.exceptions.Exceptions;

public class CreateProfile extends CreateTapp {



    String name;
Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showPopup();
Log.d("CrPr", "creating activity, starting to create activity");
        Intent intent = getIntent();
        this.name = intent.getStringExtra(FPConstants.NAME_INTENT_KEY);
        publisherNameView.setText(name);
        authorNameView.setText(name);
        addOnServiceBoundListener(new OnServiceBoundListener() {

            public void onServiceBound(VouchService vouchService) {
                Log.d("CrPr-OSBL", "on service bound method called");
                if(vouchService.equals(CreateProfile.this.vouchService)) {
                    vouchService.addVouchUserCreatedListener(new VouchService.VouchUserCreatedListener() {
                        @Override
                        public void onVouchUserCreated(VouchUser vouchUser) {
                            if (CreateProfile.this.vouchUser.equals(vouchUser)) {
                                runOnUiThread(new LayoutProfile());
                            }else{
                                Log.d("CrPr", "we seem to have a mismatched user problem here");
                            }
                        }
                    });
                }else{
                    Log.d("CrPr", "we seem to have a mismatched service problem here");
                }
            }
        });
       bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher_foreground);
       showAlert(name, R.string.locksmith_working_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //layoutProfile();
            }
        });


    }
    private class LayoutProfile implements  Runnable {
        public void run() {
            tweetView.setText(vouchUser.getProfileString());
            titleView.setText(vouchUser.getTwitterUser().name);
            cameraPreview.setVisibility(View.INVISIBLE);
            try {

                illustrationView.setImageBitmap(vouchUser.getAvatar());
                illustrationView.setVisibility(View.VISIBLE);
            } catch (Exception x) {
                Exceptions.dump(x);
            }

        }
    }
}
