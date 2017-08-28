package com.dhc.library.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/28 14:46
 * 描述 ：{@link GlideLoader}
 */
public abstract class LoadCallback {
  public   void onLoadFailed(Throwable e,Drawable errorDrawable) {}

    public abstract void onLoadReady(Bitmap bitmap);

    void onLoadCanceled() {}
}
