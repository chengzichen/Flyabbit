package com.dhc.flyabbit.home.presenter.contract;

import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.library.base.IBaseModle;
import com.dhc.library.base.IBasePresenter;
import com.dhc.library.base.IBaseView;

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

    }

    interface IModle extends IBaseModle {

        Flowable<GankApiResponse<List<GankItemBean>>> getTechList(String tech, int page);

    }

}
