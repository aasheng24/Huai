package com.cmit.facedetectdemo;

public class FaceCheckEntity {
    private String mId;
    private String mPath;
    private String[] mImgBase64s;

    public FaceCheckEntity(String id, String path, String[] base64s) {
        mId = id;
        mPath = path;
        mImgBase64s = base64s;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String[] getmImgBase64s() {
        return mImgBase64s;
    }

    public void setmImgBase64s(String[] mImgBase64s) {
        this.mImgBase64s = mImgBase64s;
    }
}
