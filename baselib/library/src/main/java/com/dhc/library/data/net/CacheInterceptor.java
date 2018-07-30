package com.dhc.library.data.net;


import android.content.Context;

import com.dhc.library.utils.sys.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @creator:denghc(desoce)
 * @updateTime:2018/7/30 13:47
 * @description:  Cache policy interceptor class  ,add  cache to okHttp .
 */
public class CacheInterceptor implements Interceptor {
    private Context mContext;

    public CacheInterceptor(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetworkUtil.isConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtil.isConnected(mContext)){//Determine whether the network is connected
            int maxAge = 0;
            // Set the cache timeout of 0 hours when there is a network
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // When there is no network, set the timeout to 4 weeks
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
