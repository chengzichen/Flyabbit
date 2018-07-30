package com.dhc.flyabbit.gank.presenter.contract;

import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.library.framework.IBaseModle;
import com.dhc.library.framework.IBasePresenter;
import com.dhc.library.framework.IBaseView;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 创建者：邓浩宸
 * 时间 ：2017-04-25 下午 5:40
 * 描述 ：干货妹子契约类
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
