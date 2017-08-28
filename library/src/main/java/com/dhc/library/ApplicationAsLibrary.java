package com.dhc.library;

import android.app.Application;

/**
 * Created by 邓浩宸 on 17/2/27.
 * 作为接口，方便主工程调度子模块的声明周期
 */

public interface ApplicationAsLibrary {

  public void onCreateAsLibrary(Application application);
  public void onLowMemoryAsLibrary(Application application);
  public void onTrimMemoryAsLibrary(Application application, int level);

}
