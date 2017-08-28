package com.dhc.library.constant;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/17 9:53
 * 描述 ：ActionBar 右侧按钮，可定制icon和点击事件
 */
public abstract class OptionsButton implements Serializable {

    // 图标drawable id
    public int iconId;

    //右侧为textview时的字符的颜色
    public int rightColor;

    //右侧为textview时的字符
    public String rightString;

    //左侧为textview时的字符
    public String navigateString;

    //左侧为textview时的字符
    public boolean needNavigateIocn;

    //左侧为textview时的字符的颜色
    public int navigateColor;


    public OptionsButton iconId(int iconId) {
        this.iconId = iconId;
        return this;
    }

    public OptionsButton rightColor(int rightColor) {
        this.rightColor = rightColor;
        return this;
    }

    public OptionsButton navigateString(String navigateString) {
        this.navigateString = navigateString;
        return this;
    }

    public OptionsButton needNavigateIocn(boolean needNavigateIocn) {
        this.needNavigateIocn = needNavigateIocn;
        return this;
    }

    public OptionsButton rightString(String rightString) {
        this.rightString = rightString;
        return this;
    }

    public OptionsButton navigateColor(int navigateColor) {
        this.navigateColor = navigateColor;
        return this;
    }

    // 响应事件
    public abstract void onClick(Context context, View view, String sessionId);
}