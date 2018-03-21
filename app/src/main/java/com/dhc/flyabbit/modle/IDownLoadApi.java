package com.dhc.flyabbit.modle;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 创建者     邓浩宸
 * 创建时间   2018/3/21 17:07
 * 描述	      ${TODO}
 */

public interface IDownLoadApi {


    @GET
    @Streaming
    Flowable<ResponseBody> download( @Url String url);
}
