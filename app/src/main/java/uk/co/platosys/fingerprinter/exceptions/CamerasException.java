package uk.co.platosys.fingerprinter.exceptions;

import android.hardware.camera2.CameraDevice;

/**
 * Created by edward on 20/02/18.
 */

public class CamerasException extends Exception{
    public CamerasException(String msg){
        super(msg);
    }
    public CamerasException(String msg, Throwable t){
        super(msg, t);
    }
}
