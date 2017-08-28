package com.dhc.library.utils;

import android.app.Application;

import com.dhc.library.ApplicationAsLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/29 11:55
 * 描述	      ${TODO}
 */
public class AsLibUtil {
    private static List<ApplicationAsLibrary> mChildApplicationList = new ArrayList<>();

    public static void addAsLIbChild(ApplicationAsLibrary applicationAsLibrary){
        mChildApplicationList.add(applicationAsLibrary);
    }

    public static void  doCreateAsLibrary(Application application){
        for (ApplicationAsLibrary app : mChildApplicationList) {
            app.onCreateAsLibrary(application);
        }
    }
}
