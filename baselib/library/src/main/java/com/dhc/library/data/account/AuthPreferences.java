package com.dhc.library.data.account;

import android.content.Context;
import android.text.TextUtils;

import com.dhc.library.data.SPHelper;

public class AuthPreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_ACCESSTOKEN = "accessToken";
    private static final String KEY_USER_DATA = "user_data";
    private static final String KEY_QR_DATA = "qr_data";
    private Context mContext;

    public AuthPreferences(Context context) {
        mContext = context;
    }

    public void setUser(String user) {
        SPHelper.put(mContext, KEY_USER, user);
    }

    public void setAccessToken(String token) {
        SPHelper.put(mContext, KEY_ACCESSTOKEN, token);
    }

    public void setUserData(String userData) {
        SPHelper.put(mContext, KEY_USER_DATA, userData);
    }


    public String getUser() {
        return (String) SPHelper.get(mContext, KEY_USER, "");
    }

    public String getAccessToken() {
        return SPHelper.get(mContext, KEY_ACCESSTOKEN, "").toString();
    }

    public String getQRData(){
        return SPHelper.get(mContext,KEY_QR_DATA,"").toString();
    }

    public void setQRData(String qrData){
        SPHelper.put(mContext,KEY_QR_DATA,qrData);
    }

    public String getUserData() {
        return (String) SPHelper.get(mContext, KEY_USER_DATA, "");
    }

    public void clear() {
        SPHelper.clear(mContext);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getAccessToken());
    }

}
