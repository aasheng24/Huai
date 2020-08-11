package com.android.huai.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class FaceView extends AppCompatImageView {
    private FaceDetector.Face[] faces;
    private Paint paint;
    private Bitmap bitmap;

    private float left,top,right,bottom;
    private int x,y,width;


    public FaceView(Context context) {
        super(context);
        init(context);
    }

    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setFaces(FaceDetector.Face[] faces, Bitmap bitmap) {
        if (faces != null && faces.length > 0) {
            this.faces = faces;
            this.bitmap = bitmap;
            setImageBitmap(bitmap);
            calculateFaceArea();
            invalidate();
        } else {

        }
    }

    private void calculateFaceArea() {
        float eyesDistance = 0;
        for (int i = 0; i < faces.length; i ++) {
            FaceDetector.Face face = faces[i];
            if (face != null) {
                PointF pointF = new PointF();
                face.getMidPoint(pointF);//获取人脸中心点
                eyesDistance = face.eyesDistance();//获取人脸眼睛间距
                //计算人脸的区域
                float delta = eyesDistance / 2;
                left = (pointF.x - eyesDistance) / scaleRate;
                top = (pointF.y - eyesDistance + delta) / scaleRate;
                right = (pointF.x + eyesDistance) / scaleRate;
                bottom = (pointF.y + eyesDistance + delta) / scaleRate;

                x = (int) (pointF.x - eyesDistance);
                y = (int) (pointF.y - eyesDistance + delta);
                width = (int) (eyesDistance * 2);
            }
        }
    }

    private float scaleRate = 1.0f;

    public void setScaleRate(float rate) {
        this.scaleRate = rate;
    }

    public void clear() {
        this.faces = null;
        postInvalidate();
    }

    /** 获取人脸区域，适当扩大了一点人脸区域 */
    public Bitmap getFaceArea(){
        if(this.bitmap != null) {
            int bmWidth = bitmap.getWidth();
            int bmHeight = bitmap.getHeight();
            int delta = 50;
            width += 50;
            int height = width;
            x = (int) (left - delta);
            y = (int) (top - delta);
            if(x < 0) {
                x = 0;
            }
            if(y < 0) {
                y = 0;
            }
            if(width > bmWidth) {
                width = bmWidth;
            }
            if(height > bmHeight) {
                height = bmHeight;
            }
            return Bitmap.createBitmap(bitmap, x, y, width, height);
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.faces == null || faces.length == 0) {
            return ;
        }
        canvas.drawRect(left, top, right, bottom, paint);
    }


}
