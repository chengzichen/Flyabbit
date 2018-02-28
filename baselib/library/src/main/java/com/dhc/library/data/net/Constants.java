package com.dhc.library.data.net;

import com.dhc.library.utils.rx.BaseSubscriberListener;
import com.dhc.library.utils.rx.RxBus;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    public static final String VERSION = "version";

    // 公钥字符串
    public static final String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsogx03aD0eGNW8XSI4tJG0Ju9" +
            "lL6+1F6YkazUTGF9rMd8iSKhut9MIjAJIB3E4UI2Pd6GGIZASPbiYVfi5RkkQx7P" +
            "Clyb2FTLZjmvXlquAt4P3V7n0moCEfbapD975VWYoV93MBcq31xZBQJDZgzk2Y/S" +
            "HShL4OdRNkgKZRQ+lQIDAQAB";

    //================#cache=============================
    public static final String CACHE_SP_NAME = "config";
    public static final String CACHE_DISK_DIR = "cache";
    // #router

    //=====================网络请求====================================

    public final static String GANK_URL = "http://gank.io/api/";
    public final static String BASE_URL = "http://api.98095.net/";
    public final static String BASE_MOBILE_URL = "http://m.98095.net/";
    public final static String BASE_IMAGE_URL = "http://res.98095.net/";

    public final static String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjRmMWcyM2ExMmFhIn0.eyJpc3MiOiJodHRwOlwvXC9leGFtcGxlLmNvbSIsImF1ZCI6Imh0dHA6XC9cL2V4YW1wbGUub3JnIiwianRpIjoiNGYxZzIzYTEyYWEiLCJpYXQiOjE1MTA4MjEyMjQsIm5iZiI6MTUxMDgyMTI4NCwiZXhwIjoxNTEwOTA3NjI0LCJ1aWQiOiIyODIiLCJ0eXBlIjoidXNlciJ9.HGiMq-ZoWmMYB6dN_C9OojsHkRwiuqBmfpNJZY9l3k8";
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
     * {@link RxBus}
     * {@link com.fanyu.joys.home.login.ui.ScanLoginFragment}
     * {@link com.fanyu.joys.home.login.ui.LoginFragment}
     */
    public static final int LOGIN_SUCCEED = 200;
    /**
     * 验证登录
     * {@link BaseSubscriberListener#checkReLogin(java.lang.String, java.lang.String)}
     * {@link com.fanyu.joys.home.game.ui.FastBetFragment#onClick(android.view.View)}
     */
    public static final int GO_LOGIN = 401;


    /**
     *退出登录
     */
    public static final int LOGINOUT = 400;


    /**
     * {@link com.fanyu.joys.home.game.ui.FastBetListFragment}
     */
    public static final int NOTIFY_CHANGE = 21;

    /**
     * 投注成功更新钱数量
     */
    public static final int BET_SUCCESS = 22;



    //=====================Rxbus事件总线Code End====================================


}
