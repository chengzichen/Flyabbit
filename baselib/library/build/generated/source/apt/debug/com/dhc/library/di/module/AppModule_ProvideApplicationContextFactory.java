package com.dhc.library.di.module;

import com.dhc.library.base.BaseApplication;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideApplicationContextFactory implements Factory<BaseApplication> {
  private final AppModule module;

  public AppModule_ProvideApplicationContextFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public BaseApplication get() {
    return Preconditions.checkNotNull(
        module.provideApplicationContext(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<BaseApplication> create(AppModule module) {
    return new AppModule_ProvideApplicationContextFactory(module);
  }

  public static BaseApplication proxyProvideApplicationContext(AppModule instance) {
    return instance.provideApplicationContext();
  }
}
