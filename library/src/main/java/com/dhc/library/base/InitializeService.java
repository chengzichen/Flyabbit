package com.dhc.library.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.dhc.library.utils.logger.KLog;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/23 12:52
 * 描述 ：初始化的服务
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.kairu.library.base.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {


        //log日志
        KLog.init("dhc").hideThreadInfo().methodCount(1);



//        if (AppUtil.isDebug()) {
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init(this.getApplication()); // 尽可能早，推荐在Application中初始化





        //初始化过度绘制检测
        //BlockCanary.install(this, new AppBlockCanaryContext()).start();


    }
}