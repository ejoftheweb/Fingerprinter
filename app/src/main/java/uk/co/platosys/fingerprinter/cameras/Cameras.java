package uk.co.platosys.fingerprinter.cameras;

import android.content.Context;
import android.util.Log;
import android.view.TextureView;
import android.widget.TextView;

/**
 * Created by edward on 06/03/18.
 */

public class Cameras {
    public static final int FRONT_CAMERA= 2;
    public static final int REAR_CAMERA=4;
    public int activeCamera=REAR_CAMERA;
    CamerasThread camerasThread;
    Context context;
    TextureView cameraPreview;
    public Cameras(Context context, TextureView cameraPreview) {
        this.context = context;
        this.cameraPreview = cameraPreview;

        camerasThread = new CamerasThread(context, cameraPreview);
        camerasThread.run();
        Log.d("cameras" ,"cameras thread called and started, returning from main thread constructor");
    }

    public void selectCamera(int camera){
        camerasThread.selectCamera(camera);
    }
    public void startBackgroundThread(){
        camerasThread.startBackgroundThread();
    }
    public void stopBackgroundThread(){
        camerasThread.stopBackgroundThread();
    }
}


