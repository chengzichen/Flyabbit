package com.dhc.library.utils;

/**
 * 创建者      Deemons
 * 创建时间     2016/6/13 0:18
 * 包名       com.duocai100.duocai.utils
 * 描述       ${TODO}
 * <p/>
 * 更新者      Deemons
 * 更新时间     2016/6/13
 * 更新描述
 */
/*
 * Created by w4lle 2016 .
 * Copyright (c) 2016 Boohee, Inc. All rights reserved.
 */


import java.math.BigDecimal;

/**
 * 浮点数精确计算工具类
 *
 */
public class ArithmeticUtil {

    /**
     * 对一个数字取精度
     *
     * @param v
     * @param scale
     * @return
     */
    public static float round(float v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 精确加法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static float add(float v1, float v2) {
        BigDecimal bigDecimal1 = new BigDecimal(v1);
        BigDecimal bigDecimal2 = new BigDecimal(v2);
        return bigDecimal1.add(bigDecimal2).floatValue();
    }

    /**
     * 精确加法
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static float addWithScale(float v1, float v2, int scale) {
        BigDecimal bigDecimal1 = new BigDecimal(v1);
        BigDecimal bigDecimal2 = new BigDecimal(v2);
        return bigDecimal1.add(bigDecimal2).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 精确减法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static float sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).floatValue();
    }

    /**
     * 精确乘法,默认保留一位小数
     *
     * @param v1
     * @param v2
     * @return
     */
    public static float mul(float v1, float v2) {
        return mulWithScale(v1, v2, 1);
    }

    /**
     * 精确乘法，保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static float mulWithScale(float v1, float v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return round(b1.multiply(b2).floatValue(), scale);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 取余数
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int remainder(float v1, float v2) {
        return Math.round(v1 * 100) % Math.round(v2 * 100);
    }

    /**
     * 比较大小 如果v1 大于v2 则 返回true 否则false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean strCompareTo(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        int bj = b1.compareTo(b2);
        boolean res;
        if (bj > 0)
            res = true;
        else
            res = false;
        return res;
    }
}