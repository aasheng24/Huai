package com.android.huai.widget.dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public class DialogUtil {
    public static CustomDialog getLongMultiDialog(@NonNull FragmentManager manager, String title, String content, String confirm, String cancel, int color, int... indexs) {
        Bundle data = new Bundle();
        data.putString("extra_confirm", confirm);
        data.putString("extra_cancel", cancel);
        data.putString("extra_prompt", content);
        data.putString("extra_title", title);
        data.putInt("extra_color", color);
        data.putIntArray("extra_index_array", indexs);
        return CustomDialog.getInstance(manager, CustomDialog.TAG, data);
    }
}
