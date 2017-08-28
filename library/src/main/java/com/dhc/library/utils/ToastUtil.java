package com.dhc.library.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.library.R;


/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:01
 * 描述 ：Toast提示类
 */
public class ToastUtil {

    static ToastUtil td;
    Context context;
    static Toast toast;
    String msg;
    TextView tvMsg;
    public static void show(Context contex,int resId) {
        show(contex,contex.getString(resId));
    }

    public static void show(Context contex,String msg) {
        if (contex==null){
            return;
        }
        if (td == null) {
            td = new ToastUtil(contex);
        }
        if (toast==null){
            toast=  td.create();
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        td.setText(msg);
        toast.show();
    }
    public static void shortShow(Context contex,String msg) {
        if (contex==null){
            return;
        }
        if (td == null) {
            td = new ToastUtil(contex);
        }

        if (toast==null){
         toast=  td.create();
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        td.setText(msg);
        toast.show();
    }



    public ToastUtil(Context context) {
        this.context = context;
    }
    //TODO 居中显示
    public Toast create() {
        View contentView = View.inflate(context, R.layout.layout_dialog_toast, null);
        tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        tvMsg.setText(msg);
        return toast;
    }
    //TODO 据下方显示
    public Toast create_bottom() {
        View contentView = View.inflate(context, R.layout.layout_dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }



    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    private void setText(String text) {
        msg = text;
        tvMsg.setText(msg);
    }


    public static void disposeFailureInfo(Throwable t, Context context, View view) {
        if (t.toString().contains("GaiException") || t.toString().contains("SocketTimeoutException") ||
                t.toString().contains("UnknownHostException")) {
            Snackbar.make(view, "网络不好,~( ´•︵•` )~", Snackbar.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
