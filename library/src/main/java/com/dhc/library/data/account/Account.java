package com.dhc.library.data.account;

public interface Account {

    String name();

    String accessToken();

    String refreshToken();

    String toJson();

    String getUserId();

}
