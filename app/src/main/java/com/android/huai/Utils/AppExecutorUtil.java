package com.android.huai.Utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class AppExecutorUtil {
    private static final String TAG = "AppExecutorUtil";
    /**磁盘IO线程池**/
    private final Executor mDiskIO;
    /**网络IO线程池**/
    private final Executor mNetworkIO;
    /**UI线程**/
    private final Executor mMainThread;
    /**定时任务线程池**/
    private final ScheduledThreadPoolExecutor schedule;

    private static AppExecutorUtil instance;
    private static Object object = new Object();

    public static AppExecutorUtil getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new AppExecutorUtil();
                }
            }
        }
        return instance;
    }

    private AppExecutorUtil() {

        this.mDiskIO = Executors.newSingleThreadExecutor(new MyThreadFactory("single"));

        this.mNetworkIO = Executors.newFixedThreadPool(3, new MyThreadFactory("fixed"));

        this.mMainThread = new MainThreadExecutor();

        this.schedule = new ScheduledThreadPoolExecutor(5, new MyThreadFactory("sc"), new ThreadPoolExecutor.AbortPolicy());
    }

    class MyThreadFactory implements ThreadFactory {

        private final String name;
        private int count = 0;

        MyThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            count++;
            return new Thread(r, name + "-" + count + "-Thread");
        }
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public ScheduledThreadPoolExecutor schedule() {
        return schedule;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
