package uk.co.platosys.fingerprinter.activities;

import android.Manifest;
import android.content.pm.PackageManager;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.fingerprinter.cameras.Cameras;
import uk.co.platosys.fingerprinter.exceptions.CamerasException;
import uk.co.platosys.fingerprinter.models.VouchUser;
import uk.co.platosys.fingerprinter.services.VouchService;
import uk.co.platosys.minigma.exceptions.Exceptions;


/** This is the base class for writable Vouch activities.
 *
 */
public class CreateTapp extends TappActivity {
      static final int PERMISSIONS_REQUEST = 169;
@BindView (R.id.camera_preview)
TextureView cameraPreview;
Cameras cameras;
String TAG = "CT";
VouchUser vouchUser;



 private final String[] permissionsNeeded = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CT", "onCreate called");
        setContentView(R.layout.activity_tapp);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissionsNeeded,  PERMISSIONS_REQUEST);
        }else{
            try {
                this.cameras = new Cameras(this, cameraPreview);
            }catch(Exception x){
                Exceptions.dump(x);
            }
        }
        addOnServiceBoundListener(new OnServiceBoundListener() {
            @Override
            public void onServiceBound(VouchService vouchService) {
                if(vouchService.equals(CreateTapp.this.vouchService)) {
                    vouchService.addVouchUserCreatedListener(new VouchService.VouchUserCreatedListener() {
                        @Override
                        public void onVouchUserCreated(VouchUser vouchUser) {
                            CreateTapp.this.vouchUser = vouchUser;
                        }
                    });
                }else{
                    Log.d("CT", "we seem to have a problem here");
                }
            }
        });




        // Optional: Hide the status bar at the top of the window
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the content view and get references to our views


        ArrayAdapter<CharSequence> imageSourceSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.imageoptions, android.R.layout.simple_spinner_item);
        imageSourceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        imageSourceSpinner.setAdapter(imageSourceSpinnerAdapter);
        imageSourceSpinner.setSelection(0);
        imageSourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:{
                        if (cameras!=null) {
                            cameras.selectCamera(Cameras.REAR_CAMERA);

                        }
                        break;
                    }
                    case 1:{
                        if (cameras!=null) {
                            cameras.selectCamera(Cameras.FRONT_CAMERA);

                        }
                        break;
                    }
                    case 2:{
                        break;
                    }
                    default:{

                    }
                }
            }

           @Override
            public void onNothingSelected (AdapterView<?> view){

           }
        });
    }






    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Get the preview size
        int previewWidth = cameraPreview.getMeasuredWidth(),
                previewHeight = cameraPreview.getMeasuredHeight();


    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(CreateTapp.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }else{
                try {
                    this.cameras = new Cameras(this, cameraPreview);
                }catch(Exception cx){
                    Exceptions.dump(cx);
                }
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        if(cameras!=null) {
            cameras.startBackgroundThread();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        //closeCamera();
        if (cameras!=null) {
            cameras.stopBackgroundThread();
        }

    }


}
