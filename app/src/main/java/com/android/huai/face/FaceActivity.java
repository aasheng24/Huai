package com.android.huai.face;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.huai.R;

public class FaceActivity extends AppCompatActivity {

    CameraView cameraView;
    FaceView faceView;
    Bitmap fullBitmap;

    private SensorManager sensorManager;
    private Sensor sensor;
    private MySensorListener mySensorListener;
    private int sensorBright = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        if(!hasFrontCamera()) {
            Toast.makeText(this, "没有前置摄像头", Toast.LENGTH_SHORT).show();
            return ;
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mySensorListener = new MySensorListener();
        sensorManager.registerListener(mySensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        initView();
    }

    private void initView(){
        cameraView = (CameraView) findViewById(R.id.camera_view);
        faceView = (FaceView) findViewById(R.id.face_view);
        cameraView.setFaceView(faceView);
        cameraView.setOnFaceDetectedListener(new CameraView.OnFaceDetectedListener() {
            @Override
            public void onFaceDetected(Bitmap bm) {
                //检测到人脸后的回调方法
                fullBitmap = bm;
                showDialog();
            }
        });
    }

    private class MySensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //光线传感器亮度改变
            sensorBright = (int) sensorEvent.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("计算结果");
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_win_layout, null);
        ImageView imageView = (ImageView) contentView.findViewById(R.id.imageview);
        TextView textView = (TextView) contentView.findViewById(R.id.textview);
        builder.setView(contentView);
        Bitmap bm = faceView.getFaceArea();
        imageView.setImageBitmap(bm);
        textView.setText("人脸区域亮度：" + getBright(bm) + "\n整幅图片亮度：" + getBright(fullBitmap) + "\n光线传感器的值：" + sensorBright);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cameraView.reset();
            }

        });
        builder.setCancelable(false);
        builder.create().show();
    }


    public int getBright(Bitmap bm) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int r, g, b;
        int count = 0;
        int bright = 0;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                count++;
                int localTemp = bm.getPixel(i, j);
                r = (localTemp | 0xff00ffff) >> 16 & 0x00ff;
                g = (localTemp | 0xffff00ff) >> 8 & 0x0000ff;
                b = (localTemp | 0xffffff00) & 0x0000ff;
                bright = (int) (bright + 0.299 * r + 0.587 * g + 0.114 * b);
            }
        }
        return bright / count;
    }

    /**
     * 判断是否有前置摄像
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean hasFrontCamera(){
        Camera.CameraInfo info = new Camera.CameraInfo();
        int count = Camera.getNumberOfCameras();
        for(int i = 0; i < count; i++){
            Camera.getCameraInfo(i, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(mySensorListener);
    }

}