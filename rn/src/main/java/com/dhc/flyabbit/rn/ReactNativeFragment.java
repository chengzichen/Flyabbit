package com.dhc.flyabbit.rn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhc.library.base.BaseFragment;
import com.dhc.library.utils.ReactManagerFactory;
import com.dhc.library.utils.delegate.OnBackToFirstListener;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

import di.HDiHelper;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/30 0030 上午 10:23
 * 描述 ：TODO 请描述该类职责
 */

public class ReactNativeFragment extends BaseFragment  implements DefaultHardwareBackBtnHandler {

    protected OnBackToFirstListener _mBackToFirstListener;
    private ReactRootView mReactRootView;
    protected ReactInstanceManager mReactInstanceManager;



    public static ReactNativeFragment newInstance() {
        ReactNativeFragment fragment = new ReactNativeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mReactRootView = new ReactRootView(_mActivity);
        mReactInstanceManager= ReactManagerFactory.getReactInstanceManager();
        mReactRootView.startReactApplication(mReactInstanceManager, "MyReactNativeApp", null);
        mView = mReactRootView;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initEventAndData(View view) {
        BaseFragment baseFragment = (BaseFragment) getParentFragment();
        if (baseFragment instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) baseFragment;
        }
//        initTitle();
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        HDiHelper.getFragmentComponent(getFragmentModule()).inject(this);
    }

//    private void initTitle() {
//        ToolBarOptions options = new ToolBarOptions()
//                .isNeedNavigate(false).titleString("微信精选");
//        setToolBar(R.id.toolbar, options);
//    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (_mBackToFirstListener != null) {
                _mBackToFirstListener.onBackToFirstFragment();
            } else {
                if (mReactInstanceManager != null) {
                    mReactInstanceManager.onBackPressed();
                } else {
                _mActivity.finish();
                }
            }
        }
        return true;
    }


    @Override
    public void invokeDefaultOnBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (_mBackToFirstListener != null) {
                _mBackToFirstListener.onBackToFirstFragment();
            } else {
                    _mActivity.finish();
            }
        }
    }




    @Override
    public void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(_mActivity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(_mActivity, this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy();
        }
    }



}
