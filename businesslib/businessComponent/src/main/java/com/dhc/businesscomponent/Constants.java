package com.dhc.businesscomponent;

import com.dhc.library.utils.rx.BaseSubscriberListener;

/**
 * 创建者：邓浩宸
 * 时间 ：2018/3/29 11:44
 * 描述 ：常量类
 */
public class Constants {

    public static final String VERSION = "version";


    //================#cache=============================
    public static final String CACHE_SP_NAME = "config";
    public static final String CACHE_DISK_DIR = "cache";
    // #router

    //=====================网络请求====================================

    public final static String GANK_URL = "http://gank.io/api/";

    /**
     * 用户未登陆
     */
    public static final String STATUS_RE_LOGIN = "40104";
    public static final String SUCCEED = "200";
    public static final String STATUS_NO_LOGIN = "40100";


    //=====================Activity和Fragment类型文件====================================


    public static final String BASE_TYPE_FRAGMENT_MAP_PATH = "raw://type_fragment_map";

    public static final String BASE_TYPE_ACTIVITY_MAP_PATH = "raw://type_activity_map";


    public static final String MOCK_FILE_UPLOAD = "";//实际地址


    //=====================Rxbus事件总线Code Start====================================
    /**
     * 登录成功
     */
    public static final int LOGIN_SUCCEED = 200;
    /**
     * 验证登录
     * {@link BaseSubscriberListener#checkReLogin(java.lang.String, java.lang.String)}
     */
    public static final int GO_LOGIN = 401;


    /**
     *退出登录
     */
    public static final int LOGINOUT = 400;


    /**
     */
    public static final int NOTIFY_CHANGE = 21;



    //=====================Rxbus事件总线Code End====================================


}
