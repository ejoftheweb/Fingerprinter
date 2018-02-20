package uk.co.platosys.fingerprinter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class CreateProfile extends AppCompatActivity {


PopupWindow popupWindow;
Button closePopupButton;
int screenWidth;
int screenHeight;
float popupProportion = FPConstants.POPUP_PROPORTIONS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        //showPopup();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth=displayMetrics.widthPixels;
        screenHeight=displayMetrics.heightPixels;
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
            View layout = inflater.inflate(R.layout.popup_locksmith,
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
