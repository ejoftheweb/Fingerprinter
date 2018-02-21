package uk.co.platosys.fingerprinter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class CreateProfile extends CreateTapp {


PopupWindow popupWindow;
Button closePopupButton;

String name;
String email;
float popupProportion = FPConstants.POPUP_PROPORTIONS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //showPopup();

        Intent intent = getIntent();
        this.name=intent.getStringExtra(FPConstants.NAME_INTENT_KEY);
        publisherNameView.setText(name);
        authorNameView.setText(name);
        titleView.setText(email);
        tweetView.setText(email);
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
       showPopup();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        popupWindow.dismiss();
    }

    private void showPopup() {
        try {
           LayoutInflater inflater = (LayoutInflater) CreateProfile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
