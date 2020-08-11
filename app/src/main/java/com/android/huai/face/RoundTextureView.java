package com.android.huai.face;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.ViewOutlineProvider;

public class RoundTextureView extends TextureView {
    private float mRadius;

    public RoundTextureView(Context context) {
        super(context);
    }

    public RoundTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Rect rect = new Rect(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
                outline.setRoundRect(rect, mRadius);
            }
        });
    }

    public void setRadius(float radius) {
        this.mRadius = radius;
    }
}
