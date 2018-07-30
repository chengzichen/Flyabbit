package com.dhc.flyabbit.home.modle;


import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.businesscomponent.Constants;
import com.hk.protocolbuffer.Result;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by codeest on 2016/8/2.
 * 干货api
 */
public interface IGankApi {

    String baseURL= Constants.GANK_URL;


    /**
     * 随机妹纸图
     */
    @GET("random/data/福利/{num}")
    Flowable<GankApiResponse<List<GankItemBean>>> getRandomGirl(@Path("num") int num);


    /**
     * 技术文章列表
     */
    @GET("data/{tech}/{num}/{page}")
    Flowable<GankApiResponse<List<GankItemBean>>> getTechList(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);

    /**
     * 技术文章列表
     */
    @Headers({"Content-Type:application/x-protobuf;charset=UTF-8","Accept: application/x-protobuf"})
    @POST()//http:// 192.168.0.79:8082/x_springboot/app/register2
    Flowable<GankApiResponse<List<GankItemBean>>> psotTest(@Url String  url,@Body Result.AppResult
            appResult);


}
