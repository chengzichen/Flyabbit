package com.dhc.library.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.dhc.library.R;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/28 14:14
 * 描述 ：Glide加载图片专用
 */
public class GlideLoader implements ILoader {

    @Override
    public void init(Context context) {

    }

    @Override
    public void loadNet(ImageView target, String url) {
        loadNet(target,url,null);
    }


    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options);
    }

    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        DrawableTypeRequest request = getRequestManager(context).load(url);
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(new SimpleTarget<GlideBitmapDrawable>() {

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (callback != null) {
                            callback.onLoadFailed(e,errorDrawable);
                        }
                    }

                    @Override
                    public void onResourceReady(GlideBitmapDrawable resource, GlideAnimation<? super GlideBitmapDrawable> glideAnimation) {
                        if (resource != null && resource.getBitmap() != null) {
                            if (callback != null) {
                                callback.onLoadReady(resource.getBitmap());
                            }
                        }
                    }

                });
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }

    @Override
    public void loadUri(ImageView target, String uri, Options options) {
        load(getRequestManager(target.getContext()).load(uri), target, options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }


    @Override
    public void needD(Context context,Target target, String url) {

//        getRequestManager(context).load(url).bitmapTransform(new MyBlurTransformation(context, 100)).into(target);
        getRequestManager(context).load(url).asBitmap().into(target);
        //    Glide.with(context).load("http://192.168.50.230:8000/"+images.get(position)).asBitmap().into(new MyTarget(photoViewAttacher));
    }

    private RequestManager getRequestManager(Context context) {
            if (context instanceof Activity) {
            return Glide.with((Activity) context);
        }
        return Glide.with(context);
    }

    private void load(DrawableTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(target);
    }

    private DrawableTypeRequest wrapScaleType(DrawableTypeRequest request, Options options) {
        if (options != null
                && options.scaleType != null) {
            switch (options.scaleType) {
                case MATRIX:
                    break;

                case FIT_XY:
                    break;

                case FIT_START:
                    break;

                case FIT_END:
                    break;

                case CENTER:
                    break;

                case CENTER_INSIDE:
                    break;

                case FIT_CENTER:
                    request.fitCenter();
                    break;

                case CENTER_CROP:
                    request.centerCrop();
                    break;
            }
        }
        return request;
    }
}
