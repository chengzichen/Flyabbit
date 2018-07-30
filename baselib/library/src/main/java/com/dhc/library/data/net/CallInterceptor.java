package com.dhc.library.data.net;

import com.dhc.library.data.HttpHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @creator：denghc(desoce)
 * @updateTime：2018/7/30 13:49
 * @description： Do before and after requests
 */
public class CallInterceptor implements Interceptor {

    HttpHelper.RequestCall call;

    public CallInterceptor(HttpHelper.RequestCall call) {
        this.call = call;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (call != null) {
            if (call.onBeforeRequest(request, chain)!=null)
            request = call.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (call != null) {
            //response.body()不能多次使用string方法
            if (call.onAfterRequest(response, response.body(), chain)!=null)
            response = call.onAfterRequest(response, response.body(), chain);
        }
        return response;
    }
}
