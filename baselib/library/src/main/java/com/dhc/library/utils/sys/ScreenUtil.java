package com.dhc.library.utils.sys;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dhc.library.utils.AppContext;

import java.lang.reflect.Field;

public class ScreenUtil {
    private static final String TAG = "Demo.ScreenUtil";

    private static double RATIO = 0.85;

    public static int screenWidth;
    public static int screenHeight;
    public static int screenMin;// 宽高中，小的一边
    public static int screenMax;// 宽高中，较大的值

    public static float density;
    public static float scaleDensity;
    public static float xdpi;
    public static float ydpi;
    public static int densityDpi;

    public static int dialogWidth;
    public static int statusbarheight;
    public static int navbarheight;

    static {
        init(AppContext.get());
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * scaleDensity + 0.5f);
    }

    public static int getDialogWidth() {
        dialogWidth = (int) (screenMin * RATIO);
        return dialogWidth;
    }

    public static void init(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;

        Log.d(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
    }

    public static int getDisplayWidth() {
        if (screenWidth == 0) {
            GetInfo(AppContext.get());
        }
        return screenWidth;
    }

    public static int getDisplayHeight() {
        if (screenHeight == 0) {
            GetInfo(AppContext.get());
        }
        return screenHeight;
    }

    public static void GetInfo(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        screenMax = (screenWidth < screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;
        statusbarheight = getStatusBarHeight(context);
        navbarheight = getNavBarHeight(context);
        Log.d(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
    }

    public static int getStatusBarHeight(Context context) {
        if (statusbarheight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusbarheight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (statusbarheight == 0) {
            statusbarheight = ScreenUtil.dip2px(25);
        }
        return statusbarheight;
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 设置dialog大小占屏幕的宽高
     * @param widthRatio 宽度比例
     * @param heightRatio 高度比例
     * @param isTrue 高度和宽度是否相等
     * @param type -> 1: 和宽度相等
     *             -> 2: 和高度相等
     */
    public static void setWindowDisplay(Dialog mDialog, Context mContext, double widthRatio, double heightRatio, boolean isTrue, int type){
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高

        if (isTrue){
            if (type == 1){
                params.width = (int) (metrics.widthPixels * widthRatio);
                params.height = (int) (metrics.widthPixels * widthRatio);
            }else{
                params.width = (int) (metrics.heightPixels * heightRatio);
                params.height = (int) (metrics.heightPixels * heightRatio);
            }
        }else{
            params.width = (int) (metrics.widthPixels * widthRatio);
            params.height = (int) (metrics.heightPixels * heightRatio);
        }

        dialogWindow.setAttributes(params);
    }

    /**
     * 设置dialog大小占屏幕的宽高
     * @param widthRatio 宽度比例
     * @param heightRatio 高度比例
     */
    public static void setWindowDisplay(PopupWindow mWindows, Context mContext, double widthRatio, double heightRatio){
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高
        mWindows.setWidth((int) (metrics.widthPixels * widthRatio));
        mWindows.setHeight((int) (metrics.heightPixels * heightRatio));
    }
}
