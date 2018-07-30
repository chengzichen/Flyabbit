package com.dhc.businesscomponent.data.net;

import android.text.TextUtils;

import com.dhc.businesscomponent.data.account.AccountManager;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/9/7 11:36
 * 描述	      ${Token管理}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = original.newBuilder();
        String access_token = AccountManager.INSTANCE.getAccessToken();
        if(!TextUtils.isEmpty(access_token)){
            requestBuilder.addHeader("Authorization", access_token);
        }

            requestBuilder.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");

        //请求体定制：统一添加token参数
        if (original.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) original.body();
            for (int i = 0; i < oidFormBody.size(); i++) {
                newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
            }
            requestBuilder.method(original.method(), newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
