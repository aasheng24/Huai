package com.remarkmedia.newretail.jninative;

public class HandleImage {
    static {
        System.loadLibrary("facedetect");
    }

    //SDK初始化
    public native boolean FaceDetectionModelInit(String faceDetectionModelPath);

    //SDK人脸检测接口
    public native float[] DetectFaces(byte[] imageDate, int imageWidth , int imageHeight
            , int imageChannel, int imgFormat);
    public native String cutFacePicture(byte[] imageDate, int imageWidth , int imageHeight, int[] rects);

    //SDK销毁
    public native boolean FaceDetectionModelUnInit();

    // 设置检测的最大人脸数
    public native void SetMaxFaces(int maxFaces);

    // 设置滤波等级
    public native void SetKalmanLevel(float kalmanLevel);

    public static native String saveCameraPicture(byte[] datas, int width, int height);

    public static native String FaceDetectCrop(byte[] imageDate, int imageWidth , int imageHeight
            , int imageChannel, int imgFormat);
}
