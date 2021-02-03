package com.android.huai.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.huai.R;


public class ToastUtils {

    /** 中间弹出toast */
    public static void toastCenter(Context context, int txt) {
        if (context != null){
            //自定义布局
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.toast, null);
            //自定义toast文本
            TextView mTextView = (TextView)view.findViewById(R.id.toast_msg);
            mTextView.setText(context.getText(txt));
            Toast toast =Toast.makeText(context,context.getText(txt),Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -10);
            toast.setView(view);
            toast.show();
        }
    }


}
