package com.dhc.library.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * 创建者：denghaochen
 * 时间 ：2016-09-07 下午 3:43
 * 描述 ：Activity进栈出栈统一管理
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    static {
        instance = new AppManager();
    }

    private AppManager() {
    }


    public static AppManager getInstance() {
        return instance;
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }


    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }
    public void finishALLExcludeCurrentActivity(Class<?> cls) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (!activityStack.get(i).getClass().equals(cls)) {
                    activityStack.get(i).finish();
                }
            }
        }
    }
    public void finishALLExcludeCurrentActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (!activityStack.get(i).equals(currentActivity())) {
                activityStack.get(i).finish();
                }
            }
        }
    }


    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用
     */
    public void AppExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }
}
