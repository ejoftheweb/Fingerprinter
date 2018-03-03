package uk.co.platosys.fingerprinter.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.fingerprinter.services.VouchService;
import uk.co.platosys.minigma.exceptions.Exceptions;

/**
 * All Vouch activities extend this base class.
 *
 * Created by edward on 21/02/18.
 */

public abstract class BaseActivity  extends AppCompatActivity {
    int screenWidth;
    int screenHeight;
    boolean bound=false;
    boolean binding=false;
    VouchService vouchService;
    String className = getClass().getName();
    List<OnServiceBoundListener> onServiceBoundListenerList = new ArrayList<>();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            VouchService.VouchBinder vouchBinder = (VouchService.VouchBinder) service;
            vouchService = vouchBinder.getService();
            Log.d("BA", className+" is bound");
            binding=true;
            for(OnServiceBoundListener onServiceBoundListener:onServiceBoundListenerList){
                onServiceBoundListener.onServiceBound();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("BA", className+" is unbound");
            binding=false;
        }
    };
    public interface OnServiceBoundListener {
        void onServiceBound();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth=displayMetrics.widthPixels;
        screenHeight=displayMetrics.heightPixels;
        bind();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(! bound){
            bind();
        }

    }

    @Override
    protected void onStop() {
        if(bound){
            unbindService(serviceConnection);
            bound=false;
        }
        super.onStop();

 }
   private void bind(){
       Intent intent = new Intent(this, VouchService.class);
       intent.putExtra("activity", this.getClass().getName());
       Log.i("BA", className+" about to bind to service");
       bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
       bound=true;
   }



    void showAlert(int title, int message, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setPositiveButton(R.string.OK, onClickListener);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    void showAlert(String title, int message, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setPositiveButton(R.string.OK, onClickListener);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public void reportBinding(){
        if(binding) {
            Log.d("BA", this.getClass().getName() + " reporting bound");
        }else{
            Log.d("BA", this.getClass().getName() + " reporting not bound");
        }
    }
    public void addOnServiceBoundListener(OnServiceBoundListener onServiceBoundListener){
        onServiceBoundListenerList.add(onServiceBoundListener);
    }

}
