package com.dhc.library.imageloader;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/17 12:02
 * 描述 ：ImageLoad工厂
 */
public class ILFactory {

    private static ILoader loader;


    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (ILFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }


}
