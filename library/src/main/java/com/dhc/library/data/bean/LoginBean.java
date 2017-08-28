package com.dhc.library.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.dhc.library.base.BaseBean;
import com.dhc.library.data.account.Account;

import java.util.List;

/**
 * 创建者     dhc
 * 创建时间   2017/4/24 15:33
 * 描述	      ${登录返回的数据}
 */
public class LoginBean extends BaseBean implements Parcelable, Account {

    private String access_token;
    @SerializedName("captcha.api")
    private String refresh_token;
    private String thirdpart_token;
    private UserInfoBean user_info;
    private String user_roles;
    private List<String> user_perms;


    protected LoginBean(Parcel in) {
        access_token = in.readString();
        refresh_token = in.readString();
        thirdpart_token = in.readString();
        user_roles = in.readString();
        user_perms = in.createStringArrayList();
    }

    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel in) {
            return new LoginBean(in);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }



    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getThirdpart_token() {
        return thirdpart_token;
    }

    public void setThirdpart_token(String thirdpart_token) {
        this.thirdpart_token = thirdpart_token;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public String getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(String user_roles) {
        this.user_roles = user_roles;
    }



    public List<String> getUser_perms() {
        return user_perms;
    }

    public void setUser_perms(List<String> user_perms) {
        this.user_perms = user_perms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(access_token);
        parcel.writeString(refresh_token);
        parcel.writeString(thirdpart_token);
        parcel.writeString(user_roles);
        parcel.writeStringList(user_perms);
    }

    @Override
    public String name() {
        if (user_info != null) {
            return user_info.getNickname();
        } else {
            return null;
        }
    }

    @Override
    public String accessToken() {
        return access_token;
    }

    @Override
    public String refreshToken() {
        return refresh_token;
    }



    @Override
    public String toJson() {
        return new GsonBuilder().create().toJson(this);
    }

    @Override
    public String getUserId() {
        return user_info != null ? user_info.id : null;
    }



    public static class UserInfoBean implements Parcelable {

        private String avatar;
        private String description;
        private String email;
        private String gender;
        private String id;
        private String loginName;
        private String mobile;
        private String nickname;
        private int credit;
        private String realName;
        private int grownNum;
        private int loveNum;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public int getGrownNum() {
            return grownNum;
        }

        public void setGrownNum(int grownNum) {
            this.grownNum = grownNum;
        }

        public int getLoveNum() {
            return loveNum;
        }

        public void setLoveNum(int loveNum) {
            this.loveNum = loveNum;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.avatar);
            dest.writeString(this.description);
            dest.writeString(this.email);
            dest.writeString(this.gender);
            dest.writeString(this.id);
            dest.writeString(this.loginName);
            dest.writeString(this.mobile);
            dest.writeString(this.nickname);
            dest.writeInt(this.credit);
            dest.writeString(this.realName);
            dest.writeInt(this.grownNum);
            dest.writeInt(this.loveNum);
        }

        public UserInfoBean() {
        }

        protected UserInfoBean(Parcel in) {
            this.avatar = in.readString();
            this.description = in.readString();
            this.email = in.readString();
            this.gender = in.readString();
            this.id = in.readString();
            this.loginName = in.readString();
            this.mobile = in.readString();
            this.nickname = in.readString();
            this.credit = in.readInt();
            this.realName = in.readString();
            this.grownNum = in.readInt();
            this.loveNum = in.readInt();
        }

        public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
            @Override
            public UserInfoBean createFromParcel(Parcel source) {
                return new UserInfoBean(source);
            }

            @Override
            public UserInfoBean[] newArray(int size) {
                return new UserInfoBean[size];
            }
        };
    }


}
