package com.dhc.flyabbit.gank.presenter.contract;

import com.dhc.library.data.net.GankApiResponse;
import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.library.base.IBaseModle;
import com.dhc.library.base.IBasePresenter;
import com.dhc.library.base.IBaseView;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 创建者：yqlee
 * 时间 ：2017-04-25 下午 5:40
 * 描述 ：爱公益契约类
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
