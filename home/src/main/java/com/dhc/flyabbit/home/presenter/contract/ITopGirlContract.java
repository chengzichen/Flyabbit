package com.dhc.flyabbit.home.presenter.contract;

import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.library.framework.IBasePresenter;
import com.dhc.library.framework.IBaseView;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author 邓浩宸
 * @date 2017/8/26 17:37
 * @description TODO
 */
public interface ITopGirlContract {

    interface IView extends IBaseView {

        void showContent(List<String> gankItemBeanList);


        void showError(String errorCode, String errorMsg);

    }

    interface IPresenter extends IBasePresenter<IView> {

        void getRandomGirl();

    }


     interface IModle {

        Flowable<GankApiResponse<List<GankItemBean>>> getRandomGirl();

    }
}
