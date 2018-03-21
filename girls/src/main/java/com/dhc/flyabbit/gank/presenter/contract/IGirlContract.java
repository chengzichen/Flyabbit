package com.dhc.flyabbit.gank.presenter.contract;

import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.library.base.IBaseModle;
import com.dhc.library.base.IBasePresenter;
import com.dhc.library.base.IBaseView;
import com.dhc.library.data.net.GankApiResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 创建者：邓浩宸
 * 时间 ：2018/3/21 17:10
 * 描述 ：
 */
public interface IGirlContract {

    interface IView extends IBaseView {

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);

        void showError(String code, String msg);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void getGirlData();

        void getMoreGirlData();

    }

    interface IModle extends IBaseModle {

        Flowable<GankApiResponse<List<GankItemBean>>> getGirlData(int page);

    }

}
