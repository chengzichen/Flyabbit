package com.dhc.timberhelper;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;

import timber.log.Timber;

/**
 * 创建者     邓浩宸
 * 创建时间   2018/3/27 15:15
 * 描述	      Log日志初始化类
 */

public class TimberInitHelper {

    public static void  init(boolean isDebug, Context context){
        if (isDebug) {
            Timber.plant(new Timber.DebugTree() {
                @Override protected void log(int priority, String tag, String message, Throwable t) {
                    Logger.log(priority, tag, message, t);
                }
            });
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                    .methodCount(0)         // (Optional) How many method line to show. Default 2
                    .methodOffset(5)        // (Optional) Hides internal method calls up to offset.
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        } else {
            Timber.plant(new CrashReportingTree());
            File file = FileUtil.makeDirs(new  File(  FileUtil.getCacheDirectory(context), "log"));
            Logger.addLogAdapter(new  DiskLogAdapter(CsvFormatStrategy.newBuilder().tag("tag")
                    .logStrategy(new DiskLogStrategy(new DiskLogHandler(file.getAbsolutePath(), BuildConfig.APPLICATION_ID, 500 * 1024))).build()));
        }
    }

}
