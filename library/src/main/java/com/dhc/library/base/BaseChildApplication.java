package com.dhc.library.base;

import android.app.Application;

import com.dhc.library.ApplicationAsLibrary;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/23 18:04
 * 描述 ：该Application只能放在子moudle中使用,用于moudle隔离
 */
public class BaseChildApplication extends BaseApplication implements ApplicationAsLibrary {

  @Override
  public void onCreate() {
    super.onCreate();
    onCreateAsLibrary(this);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    onLowMemoryAsLibrary(this);
  }

  @Override
  public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    onTrimMemoryAsLibrary(this, level);
  }

  @Override
  public void onCreateAsLibrary(Application application) {

  }

  @Override
  public void onLowMemoryAsLibrary(Application application) {

  }

  @Override
  public void onTrimMemoryAsLibrary(Application application, int level) {

  }
}
