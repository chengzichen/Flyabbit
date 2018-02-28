package com.dhc.library.base;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class XDaggerFragment_MembersInjector<T extends IBasePresenter>
    implements MembersInjector<XDaggerFragment<T>> {
  private final Provider<T> mPresenterProvider;

  public XDaggerFragment_MembersInjector(Provider<T> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static <T extends IBasePresenter> MembersInjector<XDaggerFragment<T>> create(
      Provider<T> mPresenterProvider) {
    return new XDaggerFragment_MembersInjector<T>(mPresenterProvider);
  }

  @Override
  public void injectMembers(XDaggerFragment<T> instance) {
    injectMPresenter(instance, mPresenterProvider.get());
  }

  public static <T extends IBasePresenter> void injectMPresenter(
      XDaggerFragment<T> instance, T mPresenter) {
    instance.mPresenter = mPresenter;
  }
}
