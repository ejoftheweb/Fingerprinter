package uk.co.platosys.fingerprinter.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;

import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.fingerprinter.models.VouchUser;
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
                                layoutProfile();
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
    private void layoutProfile(){
        Log.i("CrPr", "starting to layout profile");
        String description = vouchUser.getTwitterUser().description;
        Log.i("CrPr", description);
        tweetView.setText(description);
        Log.i("CrPr",description );
        titleView.setText(vouchUser.getTwitterUser().name);
        illustrationView = new ImageView(this);
        try {
            URL url = new URL(vouchUser.getTwitterUser().profileImageUrl);
            //Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageLayout.addView(illustrationView);
            illustrationView.setImageBitmap(bitmap);
        }catch (Exception x){
            Exceptions.dump(x);
        }

    }
}
