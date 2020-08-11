package com.cmit.facedetectdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityCamera";
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private CameraManager mCameraManager;
    private FaceDetectManager mFaceDetectManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFaceDetectManger = new FaceDetectManager(this);
        mCameraManager = new CameraManager(this, mFaceDetectManger);
        mFaceDetectManger.initFaceDetect();
        getPermissions();
        initView();
        String aa = "ssfd";
        aa.toCharArray();
    }

    private void getPermissions() {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    123);
        }
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.i(TAG,"surfaceCreated");
                if (mCameraManager != null) {
                    mCameraManager.openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    mCameraManager.startPreview(mSurfaceHolder);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.i(TAG,"surfaceDestroyed");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.i(TAG,"surfaceDestroyed");
                if (mCameraManager != null) {
                    mCameraManager.destroyCamera();
                }
            }
        });

    }

    @Override
    public void finish() {
        Log.i(TAG,"finish");
        super.finish();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"onDestroy");
        if (mFaceDetectManger != null) {
            mFaceDetectManger.unInitFaceDetect();
        }
        if (mCameraManager != null) {
            mCameraManager.destroyCamera();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
