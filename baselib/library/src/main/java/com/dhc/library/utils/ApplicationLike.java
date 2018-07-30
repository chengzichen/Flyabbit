package com.dhc.library.utils;

import android.app.Application;
import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by 邓浩宸 on 17/2/27.
 * 作为接口，方便主工程调度子模块的声明周期
 */

public interface ApplicationLike extends IProvider {

  public void onTerminate(Application application);

  public void onCreateAsLibrary(Application application);

  public void onLowMemoryAsLibrary(Application application);

  public void onTrimMemoryAsLibrary(Application application, int level);

  public void onConfigurationChanged(Application application, Configuration configuration);

}
