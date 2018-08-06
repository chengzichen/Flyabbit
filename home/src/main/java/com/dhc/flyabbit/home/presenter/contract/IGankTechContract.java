package com.dhc.flyabbit.home.presenter.contract;

import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.library.framework.IBaseModel;
import com.dhc.library.framework.IBasePresenter;
import com.dhc.library.framework.IBaseView;
import com.hk.protocolbuffer.Result;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author 邓浩宸
 * @date 2017/8/28 17:30
 * @description TODO
 */
public interface IGankTechContract {

    interface IView extends IBaseView {

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);

        void showError(String code, String msg);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void getTechList(String tag);

        void getMoreTechList(String tag);

        void  psotTest(String  url, Result.AppResult
                appResult);
    }

    interface IModel extends IBaseModel {

        Flowable<GankApiResponse<List<GankItemBean>>> getTechList(String tech, int page);

        Flowable<GankApiResponse<List<GankItemBean>>> psotTest(String  url, Result.AppResult
                appResult);

    }

}
