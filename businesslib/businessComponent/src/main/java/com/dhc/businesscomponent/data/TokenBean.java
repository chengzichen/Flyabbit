package com.dhc.businesscomponent.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建者：邓浩宸
 * 时间 ：2018/4/23 11:25
 * 描述 ：TODO 请描述该类职责
 */
public class TokenBean implements Parcelable{

    private String token;
    private String domain;
    private String appcode;

    public TokenBean(){}

    protected TokenBean(Parcel in) {
        token = in.readString();
        domain = in.readString();
        appcode = in.readString();
    }

    public static final Creator<TokenBean> CREATOR = new Creator<TokenBean>() {
        @Override
        public TokenBean createFromParcel(Parcel in) {
            return new TokenBean(in);
        }

        @Override
        public TokenBean[] newArray(int size) {
            return new TokenBean[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAppCode() {
        return appcode;
    }

    public void setAppCode(String appCode) {
        this.appcode = appcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(domain);
        dest.writeString(appcode);
    }
}
