package com.dhc.library.di.component;

import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.di.module.AppModule;
import com.dhc.library.di.module.AppModule_ProvideApplicationContextFactory;
import com.dhc.library.di.module.AppModule_ProvideHttpHelperFactory;
import com.dhc.library.di.module.AppModule_ProvideRxCacheDirectoryFactory;
import com.dhc.library.di.module.AppModule_ProvideRxCacheFactory;
import com.dhc.library.di.module.AppModule_RandomFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import io.rx_cache2.internal.RxCache;
import java.io.File;
import java.util.Random;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<BaseApplication> provideApplicationContextProvider;

  private Provider<HttpHelper> provideHttpHelperProvider;

  private Provider<Random> randomProvider;

  private Provider<File> provideRxCacheDirectoryProvider;

  private Provider<RxCache> provideRxCacheProvider;

  private DaggerAppComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideApplicationContextProvider =
        DoubleCheck.provider(AppModule_ProvideApplicationContextFactory.create(builder.appModule));
    this.provideHttpHelperProvider =
        DoubleCheck.provider(AppModule_ProvideHttpHelperFactory.create(builder.appModule));
    this.randomProvider = DoubleCheck.provider(AppModule_RandomFactory.create(builder.appModule));
    this.provideRxCacheDirectoryProvider =
        DoubleCheck.provider(AppModule_ProvideRxCacheDirectoryFactory.create(builder.appModule));
    this.provideRxCacheProvider =
        DoubleCheck.provider(
            AppModule_ProvideRxCacheFactory.create(
                builder.appModule, provideRxCacheDirectoryProvider));
  }

  @Override
  public BaseApplication getContext() {
    return provideApplicationContextProvider.get();
  }

  @Override
  public HttpHelper httpHelper() {
    return provideHttpHelperProvider.get();
  }

  @Override
  public Random random() {
    return randomProvider.get();
  }

  @Override
  public RxCache getRxCache() {
    return provideRxCacheProvider.get();
  }

  public static final class Builder {
    private AppModule appModule;

    private Builder() {}

    public AppComponent build() {
      if (appModule == null) {
        throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerAppComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }
  }
}
