package com.dhc.library.utils.string;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/11/25 14:30
 * 描述	      ${生成SHA256值}
 */
public class SHA256Util {

    public static final String MD5="MD5";
    public static final  String SHA_1="SHA-1";
    public static final  String SHA_256="SHA-256";
    public static final  String SHA_384="SHA-384";


    public static String sign(String str, @SignTypeChecker String type){
        String s=Encrypt(str,type);
        return s;
    }

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }


    /**
     * 替代枚举的方案，使用StringDef保证类型安全
     */
    @StringDef({MD5, SHA_1,SHA_256,SHA_384})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SignTypeChecker {

    }

}

