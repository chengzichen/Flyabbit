package com.dhc.library.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.io.File;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideRxCacheDirectoryFactory implements Factory<File> {
  private final AppModule module;

  public AppModule_ProvideRxCacheDirectoryFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public File get() {
    return Preconditions.checkNotNull(
        module.provideRxCacheDirectory(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<File> create(AppModule module) {
    return new AppModule_ProvideRxCacheDirectoryFactory(module);
  }

  public static File proxyProvideRxCacheDirectory(AppModule instance) {
    return instance.provideRxCacheDirectory();
  }
}
