package com.dhc.library.utils;

import android.content.Context;

/**
 * Created by Nirui on 17/2/21.
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
