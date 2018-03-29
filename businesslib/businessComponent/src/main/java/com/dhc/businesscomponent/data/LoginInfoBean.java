package com.dhc.businesscomponent.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.dhc.library.base.BaseBean;
import com.dhc.library.data.account.Account;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/9/27 14:40
 * 描述	      ${TODO}
 */

public class LoginInfoBean extends BaseBean implements Parcelable, Account {

    private AuthEntity auth;
    private TokenBean tokenBean;
    private int activepwd;
    private WalletEntity wallet;

    public LoginInfoBean() {
    }

    public TokenBean getTokenBean() {
        return tokenBean;
    }

    public void setTokenBean(TokenBean tokenBean) {
        this.tokenBean = tokenBean;
    }

    public AuthEntity getAuth() {
        return auth;
    }

    public void setAuth(AuthEntity auth) {
        this.auth = auth;
    }

    public int getActivepwd() {
        return activepwd;
    }

    public void setActivepwd(int activepwd) {
        this.activepwd = activepwd;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }

    @Override
    public String name() {
        if (wallet != null&&!TextUtils.isEmpty(wallet.getUser_name())) {
            return wallet.getUser_name();
        }
        else if (auth != null&&!TextUtils.isEmpty(auth.getInfo().getName())) {
            return auth.getInfo().getName();
        } else {
            return "";
        }
    }

    @Override
    public String accessToken() {
        if (tokenBean != null && !TextUtils.isEmpty(tokenBean.getToken())) {
            return tokenBean.getToken();
        } else if (auth != null && !TextUtils.isEmpty(auth.getToken())) {
            return auth.getToken();
        } else {
            return "";
        }
    }

    @Override
    public String toJson() {
        return new GsonBuilder().create().toJson(this);
    }

    @Override
    public String getUserId() {
        return auth.getInfo().getId();
    }

    @Override
    public String getRelAvatar() {
        if (auth!=null&&!TextUtils.isEmpty(auth.getInfo().avatar)){
        return auth.getInfo().avatar;
        }else if(getWallet()!=null&&!TextUtils.isEmpty(getWallet().h5_avatar)){
            return getWallet().h5_avatar;
        }else{
            return "";
        }
    }

    public static class AuthEntity implements Parcelable {


        private String token;
        private int expiration;
        private InfoEntity info;

        public AuthEntity() {
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpiration() {
            return expiration;
        }

        public void setExpiration(int expiration) {
            this.expiration = expiration;
        }

        public InfoEntity getInfo() {
            return info;
        }

        public void setInfo(InfoEntity info) {
            this.info = info;
        }

        public static class InfoEntity implements Parcelable {


            private String id;
            private String name;
            private String ranting;
            private String login_ip;
            private String email;
            private String avatar;
            private String true_name;
            private String strong;
            private String currency;
            private String last_login;
            private String nickname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRanting() {
                return ranting;
            }

            public void setRanting(String ranting) {
                this.ranting = ranting;
            }

            public String getLogin_ip() {
                return login_ip;
            }

            public void setLogin_ip(String login_ip) {
                this.login_ip = login_ip;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }

            public String getStrong() {
                return strong;
            }

            public void setStrong(String strong) {
                this.strong = strong;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getLast_login() {
                return last_login;
            }

            public void setLast_login(String last_login) {
                this.last_login = last_login;
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
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeString(this.ranting);
                dest.writeString(this.login_ip);
                dest.writeString(this.email);
                dest.writeString(this.avatar);
                dest.writeString(this.true_name);
                dest.writeString(this.strong);
                dest.writeString(this.currency);
                dest.writeString(this.last_login);
                dest.writeString(this.nickname);
            }

            public InfoEntity() {
            }

            protected InfoEntity(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
                this.ranting = in.readString();
                this.login_ip = in.readString();
                this.email = in.readString();
                this.avatar = in.readString();
                this.true_name = in.readString();
                this.strong = in.readString();
                this.currency = in.readString();
                this.last_login = in.readString();
                this.nickname = in.readString();
            }

            public static final Creator<InfoEntity> CREATOR = new Creator<InfoEntity>() {
                @Override
                public InfoEntity createFromParcel(Parcel source) {
                    return new InfoEntity(source);
                }

                @Override
                public InfoEntity[] newArray(int size) {
                    return new InfoEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.token);
            dest.writeInt(this.expiration);
            dest.writeParcelable(this.info, flags);
        }

        protected AuthEntity(Parcel in) {
            this.token = in.readString();
            this.expiration = in.readInt();
            this.info = in.readParcelable(InfoEntity.class.getClassLoader());
        }

        public static final Creator<AuthEntity> CREATOR = new Creator<AuthEntity>() {
            @Override
            public AuthEntity createFromParcel(Parcel source) {
                return new AuthEntity(source);
            }

            @Override
            public AuthEntity[] newArray(int size) {
                return new AuthEntity[size];
            }
        };
    }

    public static class WalletEntity implements Parcelable {


        private String id;
        private String uuid;
        private String name;
        private double balance;
        private double balance_before;
        private String freeze_withdraw;
        private String freeze_append;
        private String currency;
        private String updated;
        private String comment;
        private List<ChildrenEntity> children;
        private String avatar;
        private String h5_avatar;
        private String true_name;
        private String user_name;
        private double all_balance;
        private int transfer;

        protected WalletEntity(Parcel in) {
            id = in.readString();
            uuid = in.readString();
            name = in.readString();
            balance = in.readDouble();
            balance_before = in.readDouble();
            freeze_withdraw = in.readString();
            freeze_append = in.readString();
            currency = in.readString();
            updated = in.readString();
            comment = in.readString();
            children = in.createTypedArrayList(ChildrenEntity.CREATOR);
            avatar = in.readString();
            h5_avatar = in.readString();
            true_name = in.readString();
            user_name = in.readString();
            all_balance = in.readDouble();
            transfer = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(uuid);
            dest.writeString(name);
            dest.writeDouble(balance);
            dest.writeDouble(balance_before);
            dest.writeString(freeze_withdraw);
            dest.writeString(freeze_append);
            dest.writeString(currency);
            dest.writeString(updated);
            dest.writeString(comment);
            dest.writeTypedList(children);
            dest.writeString(avatar);
            dest.writeString(h5_avatar);
            dest.writeString(true_name);
            dest.writeString(user_name);
            dest.writeDouble(all_balance);
            dest.writeInt(transfer);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<WalletEntity> CREATOR = new Creator<WalletEntity>() {
            @Override
            public WalletEntity createFromParcel(Parcel in) {
                return new WalletEntity(in);
            }

            @Override
            public WalletEntity[] newArray(int size) {
                return new WalletEntity[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getBalance_before() {
            return balance_before;
        }

        public void setBalance_before(int balance_before) {
            this.balance_before = balance_before;
        }

        public String getFreeze_withdraw() {
            return freeze_withdraw;
        }

        public void setFreeze_withdraw(String freeze_withdraw) {
            this.freeze_withdraw = freeze_withdraw;
        }

        public String getFreeze_append() {
            return freeze_append;
        }

        public void setFreeze_append(String freeze_append) {
            this.freeze_append = freeze_append;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<ChildrenEntity> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenEntity> children) {
            this.children = children;
        }

        public void setBalance_before(double balance_before) {
            this.balance_before = balance_before;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getH5_avatar() {
            return h5_avatar;
        }

        public void setH5_avatar(String h5_avatar) {
            this.h5_avatar = h5_avatar;
        }

        public String getTrue_name() {
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public double getAll_balance() {
            return all_balance;
        }

        public void setAll_balance(double all_balance) {
            this.all_balance = all_balance;
        }

        public int getTransfer() {
            return transfer;
        }

        public void setTransfer(int transfer) {
            this.transfer = transfer;
        }

        public static class ChildrenEntity implements Parcelable {

            private String id;
            private String uuid;
            private String name;
            private String game_type;
            private double balance;
            private String last_updated;
            private int isexist;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGame_type() {
                return game_type;
            }

            public void setGame_type(String game_type) {
                this.game_type = game_type;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getLast_updated() {
                return last_updated;
            }

            public void setLast_updated(String last_updated) {
                this.last_updated = last_updated;
            }

            public int getIsexist() {
                return isexist;
            }

            public void setIsexist(int isexist) {
                this.isexist = isexist;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.uuid);
                dest.writeString(this.name);
                dest.writeString(this.game_type);
                dest.writeDouble(this.balance);
                dest.writeString(this.last_updated);
                dest.writeInt(this.isexist);
            }

            public ChildrenEntity() {
            }

            protected ChildrenEntity(Parcel in) {
                this.id = in.readString();
                this.uuid = in.readString();
                this.name = in.readString();
                this.game_type = in.readString();
                this.balance = in.readInt();
                this.last_updated = in.readString();
                this.isexist = in.readInt();
            }

            public static final Creator<ChildrenEntity> CREATOR = new Creator<ChildrenEntity>() {
                @Override
                public ChildrenEntity createFromParcel(Parcel source) {
                    return new ChildrenEntity(source);
                }

                @Override
                public ChildrenEntity[] newArray(int size) {
                    return new ChildrenEntity[size];
                }
            };
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.auth, flags);
        dest.writeParcelable(this.tokenBean, flags);
        dest.writeInt(this.activepwd);
        dest.writeParcelable(this.wallet, flags);
    }

    protected LoginInfoBean(Parcel in) {
        this.auth = in.readParcelable(AuthEntity.class.getClassLoader());
        this.tokenBean = in.readParcelable(TokenBean.class.getClassLoader());
        this.activepwd = in.readInt();
        this.wallet = in.readParcelable(WalletEntity.class.getClassLoader());
    }

    public static final Creator<LoginInfoBean> CREATOR = new Creator<LoginInfoBean>() {
        @Override
        public LoginInfoBean createFromParcel(Parcel source) {
            return new LoginInfoBean(source);
        }

        @Override
        public LoginInfoBean[] newArray(int size) {
            return new LoginInfoBean[size];
        }
    };
}
