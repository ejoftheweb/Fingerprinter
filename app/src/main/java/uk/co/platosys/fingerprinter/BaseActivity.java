package uk.co.platosys.fingerprinter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import uk.co.platosys.minigma.exceptions.Exceptions;

/**
 * All Vouch activities extend this base class.
 *
 * Created by edward on 21/02/18.
 */

public abstract class BaseActivity  extends AppCompatActivity {
    int screenWidth;
    int screenHeight;
    PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth=displayMetrics.widthPixels;
        screenHeight=displayMetrics.heightPixels;
    }
    void showAlert(int title, int message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    void showAlert(String title, int message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*protected void showPopup(String message) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(String header, String message) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(String header, String message, View.OnClickListener dismissListener) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(dismissListener);
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(int message) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(int header, int message) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(int header, int message, View.OnClickListener dismissListener) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(dismissListener);
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }

    protected void showPopup(String header, int message) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);

        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(String header, int message, View.OnClickListener dismissListener) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(dismissListener);
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }

    protected void showPopup(int header, String message) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);
            popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }
    protected void showPopup(int header, String message, View.OnClickListener dismissListener) {
        try {
            float popupProportion = FPConstants.POPUP_PROPORTIONS;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_regular,
                    (ViewGroup) findViewById(R.id.popup_layout));

             popupWindow = new PopupWindow(layout, (int)(screenWidth*popupProportion), (int)(screenHeight*popupProportion), true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button closePopupButton = (Button) layout.findViewById(R.id.close_popup);
            closePopupButton.setOnClickListener(dismissListener);
            TextView messageView = (TextView) findViewById(R.id.popupMessageView);
            messageView.setText(message);
            TextView headerView = (TextView) findViewById(R.id.popupHeaderView);
            headerView.setText(header);

        } catch (Exception e) {
            Exceptions.dump(e);
        }
    }*/

}
