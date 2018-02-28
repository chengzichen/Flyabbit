package com.dhc.lib.imageload.glideloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.dhc.lib.imageload.FileUtil;

import java.io.File;

/**
 * 创建者：邓浩宸
 * 时间 ：2016-09-08 下午 3:08
 * 描述 ：Glide配置工具类
 */
public class CustomCachingGlideModule implements GlideModule {

    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(() -> {
            File cacheFile = new File(FileUtil.getCacheDirectory(context), "Glide");//缓存路径
            cacheFile=  FileUtil.makeDirs(cacheFile);
            return DiskLruCacheWrapper.get(cacheFile, IMAGE_DISK_CACHE_MAX_SIZE);
        });
        //设置图片格式,默认是RGB565格式,不支持透明
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //获取MemorySizeCalculator内存缓存计算器
        MemorySizeCalculator memorySizeCalculator = new MemorySizeCalculator(context);
        //获取内存缓存大小的默认值
        int defaultMemoryCacheSize = memorySizeCalculator.getMemoryCacheSize();
        //获取内存缓存池的数量
        int defaultBitmapPoolSize = memorySizeCalculator.getBitmapPoolSize();
        //重新计算缓存的大小
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        //重新计算缓存池的大小
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        //重新设置
        LruResourceCache lruResourceCache=new LruResourceCache(customMemoryCacheSize);

        builder.setMemoryCache(lruResourceCache);

        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
//        builder.setDiskCacheService(new FifoPriorityThreadPoolExecutor())
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
