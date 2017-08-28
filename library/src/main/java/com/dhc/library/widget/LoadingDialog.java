package com.dhc.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dhc.library.R;


public class LoadingDialog {
    private Dialog dialog;
    private TextView tvTitle;
    String title = "加载中...";

    public LoadingDialog(Context context) {
        if (context==null){
            return;
        }
        dialog = new Dialog(context, R.style.dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCancelable(true);
        tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }


    public Dialog getDialog() {
        return dialog;
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setCanceledOnTouchOutside(boolean b) {
        dialog.setCanceledOnTouchOutside(b);
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener b) {
        dialog.setOnCancelListener(b);
    }
    public boolean isShowing() {
      return   dialog.isShowing();
    }



}
