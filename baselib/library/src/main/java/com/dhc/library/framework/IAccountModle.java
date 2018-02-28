package com.dhc.library.framework;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.dhc.library.base.IBaseModle;
import com.dhc.library.data.account.LoginInfoBean;
import com.dhc.library.data.net.fy.FyResponse;

import io.reactivex.Flowable;

 public interface IAccountModle extends IBaseModle, IProvider {
    Flowable<FyResponse<LoginInfoBean.WalletEntity>> getWalletEntity();
}