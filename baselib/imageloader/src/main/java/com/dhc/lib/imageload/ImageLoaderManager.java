package com.dhc.lib.imageload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.dhc.lib.imageload.glideloader.GlideImageLoader;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/9/22 15:07
 * 描述 ：图片加载框架用
 */
public class ImageLoaderManager implements IImageLoaderstrategy {
    private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();
    private IImageLoaderstrategy loaderstrategy;

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return INSTANCE;
    }

    public void setImageLoaderStrategy(IImageLoaderstrategy strategy) {
        loaderstrategy = strategy;
    }

    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url) {
        return new ImageLoaderOptions.Builder(container, url).isCrossFade(false).build();
    }

    public static ImageLoaderOptions getDefaultOptionsNew(String baseUrl, @NonNull View container, @NonNull String url) {
        if (TextUtils.isEmpty(baseUrl)){
            return null;
        }

        if (!TextUtils.isEmpty(url)&&!url.startsWith("\\/")&&url.toLowerCase().startsWith("http")){
            return new ImageLoaderOptions.Builder(container, url).isCrossFade(true).build();
        }else{
            return  new ImageLoaderOptions.Builder(container, baseUrl + url).isCrossFade(true).build();
        }

    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        if (loaderstrategy != null) {
            loaderstrategy.showImage(options);
        }
    }

    @Override
    public void hideImage(@NonNull View view, int visiable) {
        if (loaderstrategy != null) {
            loaderstrategy.hideImage(view, visiable);
        }
    }


    @Override
    public void cleanMemory(Context context) {
        loaderstrategy.cleanMemory(context);
    }

    @Override
    public void pause(Context context) {
        if (loaderstrategy != null) {
            loaderstrategy.pause(context);
        }
    }

    @Override
    public void resume(Context context) {
        if (loaderstrategy != null) {
            loaderstrategy.resume(context);
        }
    }

    // 在application的oncreate中初始化
    @Override
    public void init(Context context) {
        if (loaderstrategy == null)
            loaderstrategy = new GlideImageLoader();
        loaderstrategy.init(context);
    }

}
