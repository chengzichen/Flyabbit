package com.dhc.timberhelper;

import androidx.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.Logger;

import timber.log.Timber;

public   class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, @NonNull String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }
      if (t != null) {
        if (priority == Log.ERROR) {
          Logger.log(priority, tag, message, t);
        } else if (priority == Log.WARN) {
          Logger.log(priority, tag, message, t);
        }
      }
    }
  }