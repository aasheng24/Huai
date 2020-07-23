package com.android.huai.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.ref.SoftReference;

/**
 * 弹窗的基类
 */

public abstract class BaseDialog extends DialogFragment implements View.OnClickListener {

    /**
     * 弹窗标题
     */
    public static final String EXTRA_TITLE = "extra_title";
    /**
     * 弹窗正文
     */
    public static final String EXTRA_PROMPT = "extra_prompt";
    /**
     * 弹窗确认按钮
     */
    public static final String EXTRA_CONFIRM = "extra_confirm";

    /**
     * 弹窗的宽度,单位dp,类型int,也可使用系统常量
     * {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT}
     * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT}
     */
    public static final String EXTRA_WIDTH = "extra_width";
    /**
     * 弹窗的高度,单位dp,类型int,也可使用系统常量
     * {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT}
     * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT}
     */
    public static final String EXTRA_HEIGHT = "extra_height";

    private static final int DEFAULT_WIDTH = 288;
    private static final int DEFAULT_HEIGHT = 192;



    protected int mWidth;
    protected int mHeight;
    protected String mTitleTxt;
    protected String mPromptTxt;
    protected String mConfirmTxt;
    protected View mRootView;
    protected Context mContext;
    /**
     * 确认按钮监听
     */
    protected SoftReference<OnConfirmListener> mOnConfirmListener;
    /**
     * 取消按钮监听
     */
    protected SoftReference<OnCancelListener> mOnCancelListener;

    protected SoftReference<View.OnClickListener> mOnDismissListener;

    /**
     * 所有点击事件的处理类,防抖动
     */
    protected SupportOnClickUtil mListener = new SupportOnClickUtil();

    public BaseDialog() {
        super();
    }

    /**
     * 设置确认按钮监听
     *
     * @param listener 不可使用匿名对象
     */
    public void setOnConfirmListener(OnConfirmListener listener) {
        if (listener == null && this.mOnConfirmListener != null) {
            this.mOnConfirmListener.clear();
            this.mOnConfirmListener = null;
        } else if (listener != null) {
            this.mOnConfirmListener = new SoftReference<OnConfirmListener>(listener);
        }
    }

    /**
     * 设置取消按钮监听
     *
     * @param listener 不可使用匿名对象
     */
    public void setOnCancelListener(OnCancelListener listener) {
        if (listener == null && this.mOnCancelListener != null) {
            this.mOnCancelListener.clear();
            this.mOnCancelListener = null;
        } else if (listener != null) {
            this.mOnCancelListener = new SoftReference<OnCancelListener>(listener);
        }
    }

    public void setOnDismissListener(View.OnClickListener listener) {
        this.mOnDismissListener = new SoftReference<View.OnClickListener>(listener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            initData(savedInstanceState);
        if (getArguments() != null)
            initData(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //透明处理,使圆角正常显示
        mContext = inflater.getContext();
        //不能提前,需要使用到context
        mWidth = getPX(mWidth);
        mHeight = getPX(mHeight);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = getDialog().getWindow();
        if (window != null) {
            //消除边距
            window.getDecorView().setPadding(0, 0, 0, 0);
            //去掉四个角
            window.setBackgroundDrawableResource(android.R.color.transparent);
            setWindow(window);
        } else {
            Log.e(getClass().getSimpleName(), new Exception(" window is null").getMessage());
        }
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView(mRootView);
        return mRootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void release() {
        if (mOnConfirmListener != null) {
            mOnConfirmListener.clear();
            mOnConfirmListener = null;
        }
        if (mOnCancelListener != null) {
            mOnCancelListener.clear();
            mOnCancelListener = null;
        }
        mContext = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null && mOnDismissListener.get() != null)
            mOnDismissListener.get().onClick(null);
    }

    /**
     * 设置点击事件
     *
     * @param root 根视图
     * @param ids  控件id
     */
    protected void setOnClick(@NonNull View root, @NonNull int... ids) {
        if (ids.length == 0)
            return;
        for (int id : ids) {
            mListener.setOnClickListener(root.findViewById(id), this);
        }
    }

    /**
     * 点击关闭按钮
     */
    public void onClose() {
        dismiss();
    }


    /**
     * dp转换为px
     *
     * @param dipValue dp值
     * @return px值
     */
    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 给指定控件设置文字
     *
     * @param view 允许空控件
     */
    protected void setText(TextView view, CharSequence txt) {
        if (view != null)
            view.setText(txt);
        else
            Log.e(getClass().getSimpleName(), new Exception(" setText fial, view is a null object reference").getMessage());
    }

    /**
     * 给指定控件设置文字
     *
     * @param viewId 设置文案的控件id
     */
    protected void setText(@IdRes int viewId, CharSequence txt) {
        if (mRootView == null)
            return;
        TextView view = mRootView.findViewById(viewId);
        setText(view,txt);
    }

    /**
     * 运行于{@link #onCreate(Bundle)},如果数据不为空调用两次,分别传入savedInstanceState,Arguments
     *
     * @param data not null
     * @CreateData 2016年8月30日 下午5:08:19
     */
    protected void initData(@NonNull Bundle data) {
        if (TextUtils.isEmpty(mConfirmTxt))
            mConfirmTxt = data.getString(EXTRA_CONFIRM);
        if (TextUtils.isEmpty(mPromptTxt))
            mPromptTxt = data.getString(EXTRA_PROMPT);
        if (TextUtils.isEmpty(mTitleTxt)) {
            mTitleTxt = data.getString(EXTRA_TITLE);
        }

        if (data.containsKey(EXTRA_WIDTH)) {
            mWidth = data.getInt(EXTRA_WIDTH);
        }else {
            mWidth = DEFAULT_WIDTH;
        }
        if (data.containsKey(EXTRA_HEIGHT)) {
            mHeight = data.getInt(EXTRA_HEIGHT);
        } else {
            mHeight = DEFAULT_HEIGHT;
        }

    }

    /**
     * 获取像素值,小于0的情况视为使用系统常量
     */
    private int getPX(int dp) {
        if (dp < 0)
            return dp;
        return dip2px(dp);
    }

    /**
     * 获取弹窗布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView(@NonNull View root);

    /**
     * 设置弹窗实际宽高
     */
    protected void setWindow(@NonNull Window window) {
//        final Resources resources = mContext.getResources();
//        final int width = (int) resources.getDimension(R.dimen.dialog_body_width);
//        final int height = (int) resources.getDimension(R.dimen.dialog_body_height);
        window.setLayout(mWidth, mHeight);
    }

    /**
     * 确认按钮监听
     */
    public interface OnConfirmListener {
        void onConfirm();
    }

    /**
     * 取消按钮监听
     */
    public interface OnCancelListener {
        void onCancel();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        transaction.add(this, tag);
        int mBackStackId = transaction.commitAllowingStateLoss();
        return mBackStackId;
    }
}
