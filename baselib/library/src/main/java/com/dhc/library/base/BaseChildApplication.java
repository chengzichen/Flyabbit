package com.dhc.library.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.dhc.library.utils.ApplicationLike;



/**
 * @creator: denghc(desoce)
 * @updateTime: 2018/7/30 12:01
 * @description: This Application can only be used in the child moudle for moudle isolation
 */
public class BaseChildApplication extends BaseApplication implements ApplicationLike {

    private static final String TAG = BaseChildApplication.class.getSimpleName();

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
    public void onTerminate() {
        super.onTerminate();
        onTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged(this, newConfig);
    }

    @Override
    public void onTerminate(Application application) {
        Log.i(TAG,this.getClass().getName()+"onTerminate");
    }

    @Override
    public void onCreateAsLibrary(Application application) {
        Log.i(TAG,this.getClass().getName()+"onCreateAsLibrary");
    }

    @Override
    public void onLowMemoryAsLibrary(Application application) {
        Log.i(TAG,this.getClass().getName()+"onLowMemoryAsLibrary");
    }

    @Override
    public void onTrimMemoryAsLibrary(Application application, int level) {
        Log.i(TAG,this.getClass().getName()+"onTrimMemoryAsLibrary");
    }

    @Override
    public void onConfigurationChanged(Application application, Configuration configuration) {
        Log.i(TAG,this.getClass().getName()+"onConfigurationChanged");
    }


    @Override
    public void init(Context context) {

    }


}
