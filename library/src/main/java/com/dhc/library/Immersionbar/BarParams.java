package com.dhc.library.Immersionbar;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/26 13:50
 * 描述 ：TODO 请描述该类职责
 */
public class BarParams {
    @ColorInt
    public int statusBarColor = Color.TRANSPARENT;
    @ColorInt
    public int navigationBarColor = Color.BLACK;
    @FloatRange(from = 0f, to = 1f)
    public float statusBarAlpha = 0.0f;
    @FloatRange(from = 0f, to = 1f)
    public float navigationBarAlpha = 0.0f;
    public boolean fullScreen = false;
    public boolean fullScreenTemp = fullScreen;
    public BarHide barHide = BarHide.FLAG_SHOW_BAR;
    public boolean darkFont = false;
    @ColorInt
    public int statusBarColorTransform = Color.BLACK;
    @ColorInt
    public int navigationBarColorTransform = Color.BLACK;
    public View view;
    public Map<View, Map<Integer, Integer>> viewMap = new HashMap<>();
    @ColorInt
    public int viewColorBeforeTransform = statusBarColor;
    @ColorInt
    public int viewColorAfterTransform = statusBarColorTransform;
    public boolean fits = false;
    public int navigationBarColorTem = navigationBarColor;
    public View statusBarView;
    public View navigationBarView;
    public View statusBarViewByHeight;
}
