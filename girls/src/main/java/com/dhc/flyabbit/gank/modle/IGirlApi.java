package com.dhc.flyabbit.gank.modle;


import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.library.data.net.Constants;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public interface IGirlApi {

    String baseURL= Constants.GANK_URL;


    /**
     * 妹纸列表
     */
    @GET("data/福利/{num}/{page}")
    Flowable<GankApiResponse<List<GankItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);


}
