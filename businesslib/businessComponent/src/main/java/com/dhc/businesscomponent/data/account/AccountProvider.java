package com.dhc.businesscomponent.data.account;

public interface AccountProvider<T> {
  T provideAccount(String accountJson);
}
