package com.cmit.facedetectdemo;

import android.content.Context;

import com.remarkmedia.newretail.jninative.HandleImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FaceDetectManager {
    private static final String TAG = "FaceDetectManager";
    private Context mContext;
    protected HandleImage mHandleImage;
    private final String FACE_SUB_PATH = "facesdk/";

    public FaceDetectManager(Context context) {
        mContext = context;
        mHandleImage = new HandleImage();
    }

    public void initFaceDetect (){
        copyModeToSD("12net_gray.bin");
        copyModeToSD("24net_gray.bin");
        copyModeToSD("12net_gray.param");
        copyModeToSD("24net_gray.param");
        copyModeToSD("48net_gray.param");
        copyModeToSD("48net_gray.bin");
        copyModeToSD("12net_rgb.bin");
        copyModeToSD("24net_rgb.bin");
        copyModeToSD("12net_rgb.param");
        copyModeToSD("24net_rgb.param");
        copyModeToSD("48net_rgb.param");
        copyModeToSD("48net_rgb.bin");
        if (mHandleImage == null) {
            mHandleImage = new HandleImage();
        }
        String sdDit = CommonUtils.getBasePath(mContext);
        String sdPath = sdDit + FACE_SUB_PATH;
        boolean init = mHandleImage.FaceDetectionModelInit(sdPath);
        mHandleImage.SetMaxFaces(3);
        mHandleImage.SetKalmanLevel(0.01f);
    }

    public void unInitFaceDetect() {
        if (mHandleImage != null) {
            mHandleImage.FaceDetectionModelUnInit();
        }
    }

    private void copyModeToSD(String strOutFileName){
        String basePath = CommonUtils.getBasePath(mContext);
        File file = new File(basePath + FACE_SUB_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        String tmpFile = basePath + FACE_SUB_PATH + strOutFileName;
        File file1 = new File(tmpFile);
        if (file1.exists()) {
            return;
        }
        InputStream input = null;
        OutputStream output = null;
        try {
            input = mContext.getAssets().open(strOutFileName);
            output = new FileOutputStream(basePath + FACE_SUB_PATH + strOutFileName);
            byte[] buffer = new byte[1024];
            int length = input.read(buffer);
            while (length > 0) {
                output.write(buffer,0,length);
                length = input.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
