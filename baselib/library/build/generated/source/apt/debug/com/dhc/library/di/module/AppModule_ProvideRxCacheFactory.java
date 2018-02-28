package com.dhc.library.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.rx_cache2.internal.RxCache;
import java.io.File;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideRxCacheFactory implements Factory<RxCache> {
  private final AppModule module;

  private final Provider<File> cacheDirectoryProvider;

  public AppModule_ProvideRxCacheFactory(AppModule module, Provider<File> cacheDirectoryProvider) {
    this.module = module;
    this.cacheDirectoryProvider = cacheDirectoryProvider;
  }

  @Override
  public RxCache get() {
    return Preconditions.checkNotNull(
        module.provideRxCache(cacheDirectoryProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RxCache> create(AppModule module, Provider<File> cacheDirectoryProvider) {
    return new AppModule_ProvideRxCacheFactory(module, cacheDirectoryProvider);
  }

  public static RxCache proxyProvideRxCache(AppModule instance, File cacheDirectory) {
    return instance.provideRxCache(cacheDirectory);
  }
}
