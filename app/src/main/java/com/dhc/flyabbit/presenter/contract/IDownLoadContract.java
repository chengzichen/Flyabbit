package com.dhc.flyabbit.presenter.contract;

import com.dhc.library.base.IBaseModle;
import com.dhc.library.base.IBasePresenter;
import com.dhc.library.base.IBaseView;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * 创建者：邓浩宸
 * 时间 ：2018/3/21 17:10
 * 描述 ：TODO 请描述该类职责
 */
public interface IDownLoadContract {

    interface IView extends IBaseView {

        void showContent(Boolean downLoad);


        void showError(String code, String msg);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void download( String url,String fileName);

    }

    interface IModle extends IBaseModle {


        Flowable<ResponseBody> download( String url);

    }

}
