package com.dhc.library.data.account;

public interface AccountProvider<T> {
  T provideAccount(String accountJson);
}
