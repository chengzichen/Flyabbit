package com.dhc.library.utils.string;

import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baixiaokang on 16/12/10.
 */

public class BaseUtils {

    public static List<String> getOldWeekDays() {
        final Calendar c = Calendar.getInstance();
        String[] months = new String[8];
        for (int i = 0; i < 8; i++) {
            months[i] = new SimpleDateFormat("MM.dd").format(new Date(c
                    .getTimeInMillis()));
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        return Arrays.asList(months);
    }

    public static Paint getPaint(Paint.Style style, int color) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(style);
        mPaint.setColor(color);
        mPaint.setTextSize(30);
        return mPaint;
    }

    /**
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public static Integer evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * 验证手机格式
     */
    public static boolean phoneVerification(String phoneNum) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phoneNum)) return false;
        else return phoneNum.matches(telRegex);
    }
    /**
     * 验证密码格式
     */
    public static boolean pwVerification(String pwString) {
        String PATTERN="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";//判断6-16字母数字组合
//        String PATTERN="'/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/'";//判断6-16字母数字组合
        if (TextUtils.isEmpty(pwString)) return false;
        else return pwString.matches(PATTERN);
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            Log.e("URLEncoded","toURLEncoded error:"+paramString);
            return "";
        }

        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
            Log.e("URLEncoded","toURLEncoded error:"+paramString, localException);
        }

        return "";
    }

    /**
     * 通过正则表达式来判断。下面的例子只允许显示字母、数字和汉字。
     */
    public static boolean stringFilter(String str) {
        // 只允许字母、数字和汉字
        String regex="^[a-zA-Z\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match=pattern.matcher(str);
        return match.matches();
    }
}
