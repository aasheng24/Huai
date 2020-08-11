package com.android.huai.widget.dialog;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * 带间隔时间限制的点击事件,防抖动,通过view的id区分 <br>
 *
 * @author suma 284425176@qq.com
 * @version [1.0, 2018/7/10]
 */

public class SupportOnClickUtil implements View.OnClickListener {
    private static final String TAG = SupportOnClickUtil.class.getSimpleName();

    /**
     * 缓存点击时间,以View的id为key
     */
    private SparseArray<Long> mClickTime;
    /**
     * 实际的点击事件实现,以View的id为key
     */
    private SparseArray<WeakReference<View.OnClickListener>> mListeners;

    public SupportOnClickUtil() {
        this.mClickTime = new SparseArray<>();
        this.mListeners = new SparseArray<>();
    }

    /**
     * 增加点击事件的监听,不要使用匿名对象,会被回收
     */
    public void setOnClickListener(View view, View.OnClickListener listener) {
        if (view == null) {
            Log.w(TAG, "view is null");
            return;
        }
        view.setOnClickListener(this);
        mListeners.put(view.getId(), new WeakReference<View.OnClickListener>(listener));
    }

    /**
     * 根据id获取点击时间,判断是否需要过滤,不过滤触发点击事件
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        final WeakReference<View.OnClickListener> ref = mListeners.get(id);
        if (ref == null) {
            //Log.d(TAG, StringUtils.format("click id=%d ref is null", id));
            return;
        }
        final View.OnClickListener listener = ref.get();
        if (listener == null) {
            //Log.d(TAG, StringUtils.format("click id=%d listener is null", id));
            return;
        }
        Long time = mClickTime.get(id);
        long now = 0;
        if (time == null) {
            listener.onClick(v);
        } else if (((now = System.currentTimeMillis()) - time > 500)) {
            listener.onClick(v);
        }
        mClickTime.put(id, now);
    }

    public void release() {
        if (mListeners != null) {
            mListeners.clear();
            mListeners = null;
        }
        if (mClickTime != null) {
            mClickTime.clear();
            mClickTime = null;
        }
    }
}
