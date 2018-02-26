package uk.co.platosys.fingerprinter.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;

import java.net.URL;

import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.minigma.exceptions.Exceptions;

public class CreateProfile extends CreateTapp {



    String name;
Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showPopup();

        Intent intent = getIntent();
        this.name = intent.getStringExtra(FPConstants.NAME_INTENT_KEY);
        publisherNameView.setText(name);
        authorNameView.setText(name);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_foreground, null);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
       showAlert(name, R.string.locksmith_working_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                layoutProfile();
            }
        });

    }
    private void layoutProfile(){
        tweetView.setText(vouchService.getVouchUser().getTwitterUser().description);
        titleView.setText(vouchService.getVouchUser().getTwitterUser().name);
        illustrationView = new ImageView(this);
        try {
            URL url = new URL(vouchService.getVouchUser().getTwitterUser().profileImageUrl);
            //Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageLayout.addView(illustrationView);
            illustrationView.setImageBitmap(bitmap);
        }catch (Exception x){
            Exceptions.dump(x);
        }

    }
}
