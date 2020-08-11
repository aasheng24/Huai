package com.cmit.facedetectdemo;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraManager {
    private static final String TAG = "CameraManager";

    private Context mContext;
    private Camera mCamera;
    private FaceDetectManager mFaceDetectManager;
    private Object lockObj = new Object();
    private boolean mIsMtcnnCroping = false;
    private int mPreViewWidth, mPreviewHeight;

    public CameraManager(Context context, FaceDetectManager faceDetectManager) {
        mContext = context;
        mFaceDetectManager = faceDetectManager;
    }

    public void startPreview(SurfaceHolder surfaceHolder) {
        if (mCamera == null) {
            openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {

        }
    }

    public void initCamera() {
        if (mCamera == null) {
            Log.i(TAG, "init fail due to camera null");
        }
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPreviewFormat(ImageFormat.NV21);
        parameters.setPreviewSize(640, 480);
        mCamera.setParameters(parameters);
        Camera.Size size = mCamera.getParameters().getPreviewSize();
        mPreViewWidth = size.width;
        mPreviewHeight = size.height;
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                synchronized (lockObj) {
                    requestFaceCheck(data);
                }
            }
        });
    }

    public void openCamera(int id) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == id) {
                mCamera = Camera.open(i);
                initCamera();
            }
        }
    }

    public void destroyCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private synchronized void requestFaceCheck(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!mIsMtcnnCroping) {
                        mIsMtcnnCroping = true;
                        Log.i("ezreal","tell me the data: "+data);
                        Log.i("ezreal","tell me the wide height: "+ mPreviewHeight +" "+mPreviewHeight);
                        String path = mFaceDetectManager.mHandleImage.FaceDetectCrop(data, mPreViewWidth, mPreviewHeight,3,1);
                        if (!TextUtils.isEmpty(path)) {
                            if (path.contains("&")) {
                                String[] paths = path.split("&");
                                FaceDataUtils.processImgData(null, null, paths);
                            } else {
                                String[] paths = new String [1];
                                paths[0] = path;
                                FaceDataUtils.processImgData(null, null, paths);
                            }
                        }
                        mIsMtcnnCroping = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
