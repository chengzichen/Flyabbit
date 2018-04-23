package com.dhc.businesscomponent.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;



/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/23 12:52
 * 描述 ：初始化的服务
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.dhc.library.base.action.INIT";

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
        // TODO: 2018/4/23
    }
}