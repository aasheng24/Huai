package com.cmit.facedetectdemo;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class FaceDataUtils {
    public final static String TAG = "FaceDataUtils";

    public static void processImgData(String id, String idPath, String[] paths) {
        ArrayList<String> imgBase64s = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            String base64 = CommonUtils.fileToBase64(paths[i]);
            imgBase64s.add(base64);
            try {
                File file = new File(paths[i]);
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (imgBase64s.size() == 0) {
            return;
        }
        String[] base64s = new String[imgBase64s.size()];
        for (int i = 0; i < imgBase64s.size(); i++) {
            base64s[i] = imgBase64s.get(i);
        }
        submitFacesToH5(id,idPath,base64s);
    }

    public static void submitFacesToH5(String id, String idPath, String[] base64s) {
        Gson gson = new Gson();
        String content = gson.toJson(new FaceCheckEntity(id,idPath,base64s));
        Log.i(TAG,"the content before submit to h5: "+content);
        //传给前端
    }
}
