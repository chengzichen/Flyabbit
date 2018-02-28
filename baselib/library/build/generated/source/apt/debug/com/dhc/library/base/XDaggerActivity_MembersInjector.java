package com.dhc.library.base;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class XDaggerActivity_MembersInjector<T extends IBasePresenter>
    implements MembersInjector<XDaggerActivity<T>> {
  private final Provider<T> mPresenterProvider;

  public XDaggerActivity_MembersInjector(Provider<T> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static <T extends IBasePresenter> MembersInjector<XDaggerActivity<T>> create(
      Provider<T> mPresenterProvider) {
    return new XDaggerActivity_MembersInjector<T>(mPresenterProvider);
  }

  @Override
  public void injectMembers(XDaggerActivity<T> instance) {
    injectMPresenter(instance, mPresenterProvider.get());
  }

  public static <T extends IBasePresenter> void injectMPresenter(
      XDaggerActivity<T> instance, T mPresenter) {
    instance.mPresenter = mPresenter;
  }
}
