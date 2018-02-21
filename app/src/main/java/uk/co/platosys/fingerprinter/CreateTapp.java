package uk.co.platosys.fingerprinter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.exceptions.CameraException;
import uk.co.platosys.minigma.exceptions.Exceptions;

public class CreateTapp extends TappActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

     ImageView imageView;
    CameraManager cameraManager;
    String frontCameraID;
    String rearCameraID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CT", "onCreate called");
        this.cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String[] cameraIDs = cameraManager.getCameraIdList();
            for (String cameraID:cameraIDs){
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraID);
                if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING).equals(CameraCharacteristics.LENS_FACING_FRONT)){
                    frontCameraID=cameraID;
                }else if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING).equals(CameraCharacteristics.LENS_FACING_BACK)){
                    rearCameraID=cameraID;
                }
            }
        }catch (CameraAccessException cax){

        }

        ArrayAdapter<CharSequence> imageSourceSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.imageoptions, android.R.layout.simple_spinner_item);
        imageSourceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSourceSpinner.setAdapter(imageSourceSpinnerAdapter);
        imageSourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageLayout.removeAllViewsInLayout();
                switch (position){
                    case 0:{
                        takePicture(rearCameraID);
                        break;
                    }
                    case 1:{
                        takePicture(frontCameraID);
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
 private void takePicture(String cameraID) {
     try {
         cameraManager.openCamera(cameraID, new CameraCallBack(), null);
     }catch(SecurityException sex){
     }catch(Exception ex){

     }
 }
 private class CameraCallBack extends CameraDevice.StateCallback {
     @Override
     public void onOpened(@NonNull CameraDevice camera) {

     }

     @Override
     public void onDisconnected(@NonNull CameraDevice camera) {

     }

     @Override
     public void onError(@NonNull CameraDevice camera, int error) {
         Exceptions.dump(new CameraException("eror opening camera"+camera.getId()));
     }
 }
}
