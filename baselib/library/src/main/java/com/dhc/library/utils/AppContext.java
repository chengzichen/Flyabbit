package com.dhc.library.utils;

import android.content.Context;


/**
 * @creator:denghc(desoce)
 * @updateTime:2018/7/30 13:52
 * @description: save AppContext
 */
public class AppContext {
  public static Context mAppContext;


  public static void init(Context context) {
    if (mAppContext == null) {
      mAppContext = context.getApplicationContext();
    } else {
      throw new IllegalStateException("set context duplicate");
    }
  }

  public static Context get() {
    if (mAppContext == null) {
      throw new IllegalStateException("forget init?");
    } else {
      return mAppContext;
    }
  }
}
