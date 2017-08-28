package com.dhc.library.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.io.File;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/28 12:59
 * 描述 ：以后更换图片加载器,实现该结构就行
 */
public interface ILoader {

    void init(Context context);

    void loadNet(ImageView target, String url);

    void loadNet(ImageView target, String url, Options options);

    void loadNet(Context context, String url, Options options, LoadCallback callback);

    void loadResource(ImageView target, int resId, Options options);

    void loadAssets(ImageView target, String assetName, Options options);

    void loadFile(ImageView target, File file, Options options);

    void loadUri(ImageView target,String uri,Options options);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void resume(Context context);

    void pause(Context context);

    void needD(Context context,Target target, String url);

    class Options {
        public DiskCacheStrategy mDiskCacheStrategy;
        public int loadingResId = RES_NONE;        //加载中的资源id
        public int loadErrorResId = RES_NONE;      //加载失败的资源id
        public ImageView.ScaleType scaleType = null;

        public static final int RES_NONE = -1;

        public static Options defaultOptions() {
            return new Options(RES_NONE, RES_NONE);
        }

        public Options(int loadingResId, int loadErrorResId) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
        }

        public Options scaleType(ImageView.ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }
        public Options diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.mDiskCacheStrategy = diskCacheStrategy;
            return this;
        }
    }

}
