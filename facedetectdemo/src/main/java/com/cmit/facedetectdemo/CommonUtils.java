package com.cmit.facedetectdemo;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static String fileToBase64 (String path) {
        String ret = "";
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = fileInputStream.read(buffer)) >= 0) {
                outputStream.write(buffer,0,count);
            }
            ret = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            Log.i(TAG, "fileToBase64 error: "+e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.i(TAG, "fileToBase64 close error: "+e);
            }
        }
        return ret;
    }

    public static String getBasePath(Context context) {
        if (context == null) {
            return null;
        }
        String path = "/sdcard/";
        try {
            File dataDir = context.getApplicationContext().getExternalFilesDir(null);
            if (dataDir == null) {
                dataDir = context.getApplicationContext().getFilesDir();
            }
            if (dataDir != null) {
                return dataDir.getAbsolutePath() + File.separator;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
