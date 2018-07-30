package com.dhc.library.utils;




import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @creator denghc(desoce)
 * @updateTime 2018/7/30 13:55
 * @description 数字工具类
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
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
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


    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     *
     * @param n
     * @return
     */
    private static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n
     * @param m
     * @return
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n
     * @param m
     * @return
     */
    public static long combination(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }

    public static int simpleCircle(int num) {//简单的循环计算的阶乘
        int sum = 1;
        if (num < 0) {//判断传入数是否为负数
            throw new IllegalArgumentException("必须为正整数!");//抛出不合理参数异常
        }
        for (int i = 1; i <= num; i++) {//循环num
            sum *= i;//每循环一次进行乘法运算
        }
        return sum;//返回阶乘的值
    }





    public static int recursion(int num) {//利用递归计算阶乘
        int sum = 1;
        if (num < 0)
            throw new IllegalArgumentException("必须为正整数!");//抛出不合理参数异常
        if (num == 1) {
            return 1;//根据条件,跳出循环
        } else {
            sum = num * recursion(num - 1);//运用递归计算
            return sum;
        }
    }

    public static long addArray(int num) {//数组添加计算阶乘
        long[] arr = new long[21];//创建数组
        arr[0] = 1;

        int last = 0;
        if (num >= arr.length) {
            throw new IllegalArgumentException("传入的值太大");//抛出传入的数太大异常
        }
        if (num < 0)
            throw new IllegalArgumentException("必须为正整数!");//抛出不合理参数异常
        while (last < num) {//建立满足小于传入数的while循环
            arr[last + 1] = arr[last] * (last + 1);//进行运算
            last++;//last先进行运算，再将last的值加1
        }
        return arr[num];
    }

    public static synchronized BigInteger bigNumber(int num) {//利用BigInteger类计算阶乘

        ArrayList list = new ArrayList();//创建集合数组
        list.add(BigInteger.valueOf(1));//往数组里添加一个数值
        for (int i = list.size(); i <= num; i++) {
            BigInteger lastfact = (BigInteger) list.get(i - 1);//获得第一个元素
            BigInteger nextfact = lastfact.multiply(BigInteger.valueOf(i));//获得下一个数组
            list.add(nextfact);
        }
        return (BigInteger) list.get(num);//返回数组中的下标为num的值

    }
}