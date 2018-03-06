package uk.co.platosys.fingerprinter.cameras;


import android.content.Context;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.exceptions.CamerasException;

/* So, how it works - lots of asynchronous stuff going on here!
    we have a TextureView which shows our image
    now, we add a listener to this which listens out for its being ready.
    When it's ready, the listener onAvailable method calls the
    openCamera() method. This calls cameraManager.openCamera and passes it
    a DeviceStateCallback. When the camera is opened, it triggers the onOpened() method
    in the callback, which then calls createCameraPreview().

    createCameraPreview() then populates the CaptureRequestBuilder, calls build() on it and
    passes the resultant captureRequest to the cameraDevice along with (another!) callback.

    the callback result includes a CameraCaptureSession, which is then passed to the updatePreview
    method.

 */
/** Cameras is a class built on the Android Camera2 API intended to make using the device cameras somewhat less like
 * pulling teeth.
 * Basic usage:
 * Instantiate an object of  the class with a Context object and the TextureView in which the image is going to be
 * displayed.
 * Defaults to the rear camera; if you want a selfie, call selectCamera(Cameras.FRONT_CAMERA)
 * Image preview will show in the TextureView
 *
 * call takePhoto() or takePhoto(File saveFile)  to fix the image. Both these methods return a File object
 * which points to the saved file.
 *
 * Needs appropriate camera permissions. Since Marshmallow, these are all set at runtime, so you must
 * first do the checks BEFORE instantiating a Cameras object. If you don't you will get a SecurityException.
 *
 * Also, in the calling Activity you must override OnResume and OnPause to include calls to Cameras'
 * start and stop background threads.
 *
 *
 * Created by edward on 28/02/18.
 */

public class CamerasThread extends Thread {
    //Constants

    public static final String DEFAULT_ALBUM_NAME="PlatosysCameras";
    public static final String DEFAULT_FILENAME_ROOT="PSC_";
    //member variable declarations
    private String TAG="CamThread";
    private Context context;//arg to constructor
    private TextureView cameraPreview;//arg to constructor
    private CameraManager cameraManager; //in constructor
    private CaptureRequest.Builder previewCaptureRequestBuilder;//in constructor but fields populated later.
    private SurfaceTexture cameraPreviewSurfaceTexture;
    private CameraDevice cameraDevice;//in callback
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;//in startBackgroundThread method.
    private Size imageDimension;

    private String applicationAlbumName=DEFAULT_ALBUM_NAME;//optionally use setter
    private String applicationFilenameRoot=DEFAULT_FILENAME_ROOT;//optionally use setter

     //cameraIDs
    String frontCameraID;
    String rearCameraID;
    String activeCameraID;


    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    //constructor

    /**Creates a CamerasThread object to administer the front and rear cameras of the device
     *
     *      * @param context the context (usually an Activity) from which this is called.
     * @param cameraPreview the View - a TextureView - in which the camera image is to
     *                      be displayed.
     */
    public CamerasThread (Context context, TextureView cameraPreview)  {
        Log.d(TAG, "cameras constructor called");
        this.context=context;
        this.cameraPreview = cameraPreview;
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

     }

     public void run(){
         try {
             String[] cameraids = cameraManager.getCameraIdList();
             for(String cameraid:cameraids){
                 CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraid);
                 if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING).equals(CameraCharacteristics.LENS_FACING_BACK)){
                     rearCameraID=cameraid;
                 }else if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING).equals(CameraCharacteristics.LENS_FACING_FRONT)){
                     frontCameraID=cameraid;
                 }
             }
             activeCameraID=rearCameraID;
             startBackgroundThread();
         }catch(CameraAccessException cax){
            cax.printStackTrace();
         }
         Log.w(TAG, "cameras constructor done");
     }
    private void openCamera() throws CamerasException {
        Log.i(TAG, "open camera");

        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(activeCameraID);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            cameraManager.openCamera(activeCameraID, deviceStateCallback, null);
        }catch (SecurityException sx){
            throw new CamerasException("need camera access permission, check the AndroidManifest.xml file", sx);
        }catch (CameraAccessException e) {
            throw new CamerasException("Access problem opening camerap", e);
        }catch(Exception x) {
            Log.e(TAG, "openCamera X", x);
        }
    }
    //member variables instantiated with inner classes.
    private final CameraDevice.StateCallback deviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            //This is called when the camera is open
            Log.i(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }
        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }
        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };


    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            //open your camera here
            try {
                Log.d(TAG, "surface texture available");
                cameraPreviewSurfaceTexture=surfaceTexture;
                openCamera();
            }catch (Exception x){
                x.printStackTrace();
            }
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };
    CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            createCameraPreview();
        }
    };



    /**
     * Selects the front or rear camera as the active camera and
     * opens it.
     * @param camera
     */
    public void selectCamera(int camera){
        if(camera==Cameras.FRONT_CAMERA){
            activeCameraID=frontCameraID;

        }else if (camera==Cameras.REAR_CAMERA){
            activeCameraID=rearCameraID;
        }else{
            activeCameraID=rearCameraID;
        }
        try {
            if(cameraDevice!=null){
                cameraDevice.close();
            }
            openCamera();
        }catch(Exception x){
            x.printStackTrace();
        }
   }


    /**
     * Sets the name of the sub-directory within the DIRECTORY_PICTURES
     * folder of the external public directory where pictures taken with
     * this class are saved. It makes sense to set this with your application
     * name. If you don't call this method, the default directory name will be used.
     *
     */
    public void setApplicationAlbumName(String applicationAlbumName){
        this.applicationAlbumName=applicationAlbumName;
    }

    /**
     * Sets the filename root for pictures taken with this class. Filenames are numbered
     * incrementally within the application album.
     * @param applicationFilenameRoot
     */
    public void setApplicationFilenameRoot(String applicationFilenameRoot){
        this.applicationFilenameRoot=applicationFilenameRoot;
    }
    /**
     * Takes a picture with the active camera, saves it
     * to the next available filename in the application file directory and fixes the image
     * to the cameraPreview.
     * Returns a File object which you can use to read the saved image
     * back for the rest of your application.
     */
   public File takePicture() throws CamerasException {
       File applicationAlbum = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),applicationAlbumName);
       int num = 0;
       String saveFileName = applicationFilenameRoot+num + ".jpg";
       File saveFile = new File(applicationAlbum, saveFileName);
       while(saveFile.exists()) {
           saveFileName = applicationFilenameRoot + (num++) +".jpg";
           saveFile = new File(applicationAlbum, saveFileName);
       }
       return takePicture(saveFile);
   }
    /**
     * Takes a picture with the active camera, saves it
     * to the File provided and fixes the image
     * to the cameraPreview.
     * Returns a File object which you can use to read the saved image
     * back for the rest of your application.
     */
    public File takePicture(File saveFile) throws CamerasException {
        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(activeCameraID);
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }

            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(cameraPreview.getSurfaceTexture()));

            CaptureRequest.Builder fixCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            fixCaptureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            fixCaptureRequestBuilder.addTarget(reader.getSurface());

            // Orientation
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int rotation = windowManager.getDefaultDisplay().getRotation();
            fixCaptureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            //
            reader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (image != null) {
                            image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(saveFile);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            }, backgroundHandler);
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (cameraDevice==null) {return;}
                    try {
                        cameraCaptureSession.capture(fixCaptureRequestBuilder.build(), captureListener, backgroundHandler);
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(context, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return saveFile;
    }

    protected void createCameraPreview() {
        try {
            SurfaceTexture surfaceTexture = cameraPreview.getSurfaceTexture();
            if(surfaceTexture==null){
                Log.e(TAG, "surfaceTexture is null");
            }
            assert surfaceTexture != null;
            surfaceTexture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(surfaceTexture);
            previewCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewCaptureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            previewCaptureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                   if (cameraDevice==null) {return;}
                    updatePreview(cameraCaptureSession);
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(context, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void updatePreview(CameraCaptureSession cameraCaptureSession) {
        if(null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        try {
            //if the capture session is closed, reopen it.
            // TODO
            cameraCaptureSession.setRepeatingRequest(previewCaptureRequestBuilder.build(), null, backgroundHandler);
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }




    public void startBackgroundThread() {
        backgroundThread = new HandlerThread("Camera Background");cameraPreview.setSurfaceTextureListener(surfaceTextureListener);

        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }
   public  void stopBackgroundThread() {
        backgroundThread.quitSafely();
        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
