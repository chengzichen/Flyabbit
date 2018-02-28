package com.dhc.library.di.module;

import com.dhc.library.data.HttpHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideHttpHelperFactory implements Factory<HttpHelper> {
  private final AppModule module;

  public AppModule_ProvideHttpHelperFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public HttpHelper get() {
    return Preconditions.checkNotNull(
        module.provideHttpHelper(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<HttpHelper> create(AppModule module) {
    return new AppModule_ProvideHttpHelperFactory(module);
  }

  public static HttpHelper proxyProvideHttpHelper(AppModule instance) {
    return instance.provideHttpHelper();
  }
}
