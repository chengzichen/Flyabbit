package com.dhc.flyabbit.home.modle;


import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.library.data.net.Constants;
import com.dhc.library.data.net.GankApiResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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


}
