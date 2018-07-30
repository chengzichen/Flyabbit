package com.dhc.library.data;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/28 13:04
 * 描述	     数据处理接口类
 */
public interface IDataHelper {

    public interface RequestCall {

        Request onBeforeRequest(Request request, Interceptor.Chain chain);

        Response onAfterRequest(Response response, ResponseBody result, Interceptor.Chain chain);
    }

    public interface HttpsCall {

        void configHttps(OkHttpClient.Builder builder);

    }
    public  class NetConfig {
        public Interceptor[] mInterceptors;
        public Converter.Factory[] factories;
        public CookieJar mCookieJar;
        public RequestCall call;
        public HttpsCall mHttpsCall;
        public long connectTimeoutMills;
        public long readTimeoutMills;
        public boolean isHasLog;
        public boolean isUseRx = true;
        public String baseURL = "";
        public boolean isUseMultiBaseURL = true;

        /**
         * add okhttp Interceptors
         * @param configInterceptors
         * @return
         */
        public NetConfig configInterceptors(Interceptor[] configInterceptors) {
            this.mInterceptors = configInterceptors;
            return this;
        }
        /**
         * add okhttp Converter.Factory
         * @param factories
         * @return
         */
        public NetConfig configConverterFactory(Converter.Factory[] factories) {
            this.factories = factories;
            return this;
        }

        /**
         *  can use multi baseurl {@link com.dhc.library.data.HttpHelper#createApi(java.lang.Class,okhttp3.OkHttpClient)}
         * @param isUseMultiBaseURL
         * @return
         */
        public NetConfig configisUseMultiBaseURL(boolean isUseMultiBaseURL) {
            this.isUseMultiBaseURL = isUseMultiBaseURL;
            return this;
        }

        /**
         * root baseurl {@link com.dhc.library.data.HttpHelper#createApi(java.lang.Class,okhttp3.OkHttpClient)}
         * @param baseURL
         * @return
         */
        public NetConfig configBaseURL(String baseURL) {
            this.baseURL = baseURL;
            return this;
        }

        /**
         * config cookieManager
         * @param mCookieJar
         * @return
         */
        public NetConfig configCookieJar(CookieJar mCookieJar) {
            this.mCookieJar = mCookieJar;
            return this;
        }

        /**
         * 
         * @param call
         * @return
         */
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
