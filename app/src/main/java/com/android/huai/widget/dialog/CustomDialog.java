package com.android.huai.widget.dialog;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.android.huai.R;


/**
 * 两个按钮的长文本弹窗 <br>
 *     1.根据传入的索引集合,颜色设置索引位置对应文本的颜色
 *     2.根据传入的索引集合,设置点击内容事件
 *
 * @author suma 284425176@qq.com
 * @version [1.1, 2019-06-28] 修改监听的引用类型,改为强引用,点击文本时跳转到其他页面,回来后所有监听失效
 */

public class CustomDialog extends BaseDialog {
    public static final String TAG = CustomDialog.class.getSimpleName();

    public interface OnClickContentListener {
        /**
         * 文本特殊内容点击监听
         *
         * @param index 特殊内容的索引<br>如:[2,5,7,10],0:2~5之间的内容,1:7~10之间的内容
         */
        void onClickContent(int index/*, String content*/);
    }

    /**
     * 弹窗取消按钮
     */
    public static final String EXTRA_CANCEL = "extra_cancel";
    /**
     * color span的起始索引集合<br>
     * 如:[2,5,7,10]<br>
     * 2到5,跟7到10使用特殊颜色
     */
    public static final String EXTRA_INDEX_ARRAY = "extra_index_array";
    /**
     * color span用特殊颜色
     */
    public static final String EXTRA_COLOR = "extra_color";
    /**
     * 取消按钮文本
     */
    protected String mCancelTxt;

    /**
     * 内容
     */
    protected SpannableStringBuilder mSpanBuilder;

    /**
     * 关闭按钮监听
     */
    protected View.OnClickListener mOnCloseListener;

    /**
     * 文本点击监听
     */
    protected OnClickContentListener mOnContentListener;

    /**
     * 确认按钮监听
     */
    protected OnConfirmListener mOnConfirmListener;
    /**
     * 取消按钮监听
     */
    protected OnCancelListener mOnCancelListener;

    public boolean mDebug;

    public void setOnCloseListener(View.OnClickListener listener) {
        mOnCloseListener = listener;
    }

    public void setOnContentListener(OnClickContentListener listener) {
        mOnContentListener = listener;
    }

    /**
     * 设置确认按钮监听
     *
     * @param listener 不可使用匿名对象
     */
    @Override
    public void setOnConfirmListener(OnConfirmListener listener) {
        this.mOnConfirmListener = listener;
    }

    /**
     * 设置取消按钮监听
     *
     * @param listener 不可使用匿名对象
     */
    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        this.mOnCancelListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void release() {
        super.release();
        if (mOnCloseListener != null) {
            mOnCloseListener = null;
        }

        if (mOnContentListener != null)
            mOnContentListener = null;
    }

    /**
     * 获取弹窗布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_multi_layout;
    }

    /**
     * 获取双按钮弹窗实例
     *
     * @param manager
     * @param tag
     * @param data
     * @return
     */
    public static CustomDialog getInstance(FragmentManager manager, String tag, Bundle data) {
        CustomDialog dialogTag = (CustomDialog) manager.findFragmentByTag(tag);
        final CustomDialog dialogParent = (dialogTag == null ? new CustomDialog() : dialogTag);
        dialogParent.setArguments(data);
        if (dialogTag == null) {
            dialogParent.setCancelable(true);
        }
        return dialogParent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setCancelable(false);
        return view;
    }

    @Override
    protected void initData(@NonNull Bundle data) {
        super.initData(data);
        if (TextUtils.isEmpty(mCancelTxt)) {
            mCancelTxt = data.getString(EXTRA_CANCEL);
        }
        mSpanBuilder = new SpannableStringBuilder(mPromptTxt);
        int[] indexArray = data.getIntArray(EXTRA_INDEX_ARRAY);
        int color = data.getInt(EXTRA_COLOR);
        if (indexArray == null || indexArray.length % 2 != 0 || color == 0)
            return;
        int first;
        int end;
        int iForClick = 0;
        for (int i = 0; i < indexArray.length; i++) {
            first = indexArray[i];
            end = indexArray[++i];
            mSpanBuilder.setSpan(new ForegroundColorSpan(color), first, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            String content = mPromptTxt.substring(first, end);
            int finalIForClick = iForClick;
            mSpanBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (mOnContentListener != null) {
                        mOnContentListener.onClickContent(finalIForClick/*,content*/);
                        if (mDebug)
                            Log.d(CustomDialog.TAG, "click content :" + content);
                    }
                }

                /**
                 * Makes the text underlined and in the link color.
                 *
                 * @param ds
                 */
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
//                    super.updateDrawState(ds);
                    //不要下划线
                }
            }, first, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            iForClick++;
        }

    }

    /**
     * 初始化控件
     *
     * @param root
     */
    @Override
    protected void initView(@NonNull View root) {
        Button comfirm = root.findViewById(R.id.dialog_multi_button_confirm);
        Button cancel = root.findViewById(R.id.dialog_multi_buttom_cancel);
        TextView title = root.findViewById( R.id.dialog_multi_text_title);
        TextView content = root.findViewById( R.id.dialog_multi_text_content);
        setText(comfirm, mConfirmTxt);
        setText(title, mTitleTxt);
//		setText(content, mPromptTxt);
        if (content != null) {
            content.setText(mPromptTxt);
            //开启点击,不加点击无效
            content.setMovementMethod(LinkMovementMethod.getInstance());
        }
        setText(cancel, mCancelTxt);
        setOnClick(root,R.id.dialog_multi_button_confirm,R.id.dialog_multi_buttom_cancel);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.dialog_multi_button_confirm) {
            if (mOnConfirmListener != null)
                mOnConfirmListener.onConfirm();
            onClose();
        } else if (id == R.id.dialog_multi_buttom_cancel) {
            if (mOnCancelListener != null) {
                mOnCancelListener.onCancel();
            }
            onClose();
        }
    }

    /**
     * 设置弹窗实际宽高
     *
     * @param window
     */
    @Override
    protected void setWindow(@NonNull Window window) {
        final Resources resources = mContext.getResources();
//		Point point = UiHelper.getScreenPoin(mContext);
//		Log.d("baseDialog", StringUtils.format("屏幕 width=%d height=%d", point.x, point.y));
        final int width = (int) resources.getDimension(R.dimen.dialog_body_width);
        final int height = (int) resources.getDimension(R.dimen.dialog_body_height2);
//		Log.d("baseDialog", StringUtils.format("width=%d height=%d", width, height));
        window.setLayout(width, height);
    }


}
