package com.android.algorithmlib.designModel;

public class SingleInstance {
    private SingleInstance() {

    }

    private static volatile SingleInstance sInstance;

    public SingleInstance getsInstance() {
        if (sInstance == null) {
            synchronized (SingleInstance.class) {
                if (sInstance == null) {
                    sInstance = new SingleInstance();
                }
            }
        }
        return sInstance;
    }
}
