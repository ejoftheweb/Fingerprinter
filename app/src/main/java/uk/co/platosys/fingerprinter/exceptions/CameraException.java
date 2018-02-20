package uk.co.platosys.fingerprinter.exceptions;

import android.hardware.camera2.CameraDevice;

/**
 * Created by edward on 20/02/18.
 */

public class CameraException extends Exception{
    public CameraException(String msg){
        super(msg);
    }
}
