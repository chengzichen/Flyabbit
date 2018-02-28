package com.dhc.library.data.bean;



import com.dhc.library.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/11 11:44
 * 描述 ：toolbar的数据封装
 */
public class ToolBarOptions implements Serializable {
    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的logo资源id
     */
    public int logoId = 0;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    public int navigateId = R.mipmap.back;


    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = true;

    public ToolBarOptions titleId(int titleId) {
        this.titleId = titleId;
        return this;
    }

    public ToolBarOptions titleString(String titleString) {
        this.titleString = titleString;
        return this;
    }

    public ToolBarOptions logoId(int logoId) {
        this.logoId = logoId;
        return this;
    }

    public ToolBarOptions isNeedNavigate(boolean isNeedNavigate) {
        this.isNeedNavigate = isNeedNavigate;
        return this;
    }


    /**
     * toolbar添加右边的按钮和点击事件,可以是多个
     */
    public List<OptionsButton> mOptionsButtons;

    public ToolBarOptions addOptionsButton(OptionsButton optionsButton) {
        if (optionsButton == null) {
            return this;
        }
        if (mOptionsButtons == null)
            mOptionsButtons = new ArrayList<>();
        mOptionsButtons.add(optionsButton);
        return this;
    }

}
