package com.dhc.lib.widget;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dhc.lib.uikit.R;


public class LoadingDialog   extends Dialog {
    private TextView tvTitle;
    String title = "加载中...";

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        init();
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading_dialog);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(true);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }




    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

}
