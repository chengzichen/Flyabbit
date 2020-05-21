package com.dhc.flyabbit.my

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.alibaba.android.arouter.facade.annotation.Route
import com.dhc.lib.widget.util.ToolbarUtil
import com.dhc.library.base.BaseActivity
import com.dhc.library.base.BaseFragment
import com.dhc.businesscomponent.base.WebViewCommonFragment
import com.dhc.businesscomponent.framework.OnBackToFirstListener
import com.dhc.library.base.XDaggerFragment
import com.dhc.lib.widget.bean.ToolBarOptions
import com.dhc.library.framework.IBasePresenter
import com.dhc.library.framework.IBaseView

/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/30 0030 上午 10:23
 * 描述 ：TODO 请描述该类职责
 */
@Route(path = "/my/MyFragment")
class MyFragment : XDaggerFragment<IBasePresenter<IBaseView>,IBaseView>(), View.OnClickListener {

    protected var _mBackToFirstListener: OnBackToFirstListener? = null
    protected lateinit var mIvLogo: ImageView
    private var mGithub: TextView? = null
    private var mAuthor: TextView? = null


    override fun initEventAndData(savedInstanceState: Bundle?) {
        val baseFragment = parentFragment as BaseFragment?
        if (baseFragment is OnBackToFirstListener) {
            _mBackToFirstListener = baseFragment
        }
        initTitle()
        initView()
        initListener()
    }

    private fun initTitle() {
        val options = ToolBarOptions()
                .isNeedNavigate(false).titleString("我的")
        ToolbarUtil().setToolBar(_mActivity, `$`<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar, options, false)
        //        setToolBar(R.id.toolbar, options);
    }

    private fun initView() {
        mIvLogo = `$`(R.id.iv_logo)
        mGithub = `$`(R.id.tv_github)
        mAuthor = `$`(R.id.tv_author)
        mIvLogo.setColorFilter(ContextCompat.getColor(_mActivity, R.color.clr_6385a7))
    }

    private fun initListener() {
        mGithub!!.setOnClickListener(this)
        mAuthor!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.tv_github) {
            (_mActivity as BaseActivity).start(WebViewCommonFragment.newInstance("Github",
                    "https://github.com/chengzichen/Flyabbit", "", "", true))
        } else if (v.id == R.id.tv_author) {
            (_mActivity as BaseActivity).start(WebViewCommonFragment.newInstance("个人博客",
                    "http://blog.csdn.net/chengzichen_/article/details/77659318", "", "", true))
        }
    }

    override fun initInject(savedInstanceState: Bundle?) {

    }


    /**
     * 处理回退事件
     *
     * @return
     */
    override fun onBackPressedSupport(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            popChild()
        } else {
            if (_mBackToFirstListener != null) {
                _mBackToFirstListener!!.onBackToFirstFragment()
            } else {
                _mActivity.finish()
            }
        }
        return true
    }

    override val layoutId: Int
        get() = R.layout.fragment_my_main

}
