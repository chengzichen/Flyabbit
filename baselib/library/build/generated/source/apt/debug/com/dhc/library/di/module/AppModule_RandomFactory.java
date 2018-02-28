package com.dhc.library.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Random;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_RandomFactory implements Factory<Random> {
  private final AppModule module;

  public AppModule_RandomFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Random get() {
    return Preconditions.checkNotNull(
        module.random(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Random> create(AppModule module) {
    return new AppModule_RandomFactory(module);
  }
}
