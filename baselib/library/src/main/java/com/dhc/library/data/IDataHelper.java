package com.dhc.library.data;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/28 13:04
 * 描述	      ${TODO}
 */
public interface IDataHelper {

    public interface RequestCall {

        Request onBeforeRequest(Request request, Interceptor.Chain chain);

        Response onAfterRequest(Response response, ResponseBody result, Interceptor.Chain chain);
    }

    public interface HttpsCall {

        void configHttps(OkHttpClient.Builder builder);

    }
    public static class NetConfig {
        public Interceptor[] mInterceptors;
        public CookieJar mCookieJar;
        public RequestCall call;
        public HttpsCall mHttpsCall;
        public long connectTimeoutMills;
        public long readTimeoutMills;
        public boolean isHasLog;
        public boolean isUseRx = true;

        public NetConfig configInterceptors(Interceptor[] configInterceptors) {
            this.mInterceptors = configInterceptors;
            return this;
        }

        public NetConfig configInterceptors(CookieJar mCookieJar) {
            this.mCookieJar = mCookieJar;
            return this;
        }

        public NetConfig configCall(RequestCall call) {
            this.call = call;
            return this;
        }

        public NetConfig configConnectTimeoutMills(long connectTimeoutMills) {
            this.connectTimeoutMills = connectTimeoutMills;
            return this;
        }

        public NetConfig configReadTimeoutMills(long readTimeoutMills) {
            this.readTimeoutMills = readTimeoutMills;
            return this;
        }

        public NetConfig configLogEnable(boolean isHasLog) {
            this.isHasLog = isHasLog;
            return this;
        }

        public NetConfig configIsUseRx(boolean isUseRx) {
            this.isUseRx = isUseRx;
            return this;
        }

        public NetConfig configHttps(HttpsCall mHttpsCall) {
            this.mHttpsCall = mHttpsCall;
            return this;
        }
    }

    <S> S getApi(Class<S> serviceClass);

    <S> S createApi(Class<S> serviceClass);

    <S> S getApi(Class<S> serviceClass, OkHttpClient client);

    <S> S createApi(Class<S> serviceClass, OkHttpClient client);

    OkHttpClient  getClient();

    void initConfig(HttpHelper.NetConfig netConfig);
}
