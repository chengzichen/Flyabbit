package com.dhc.library.di.module;

import android.app.Activity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class FragmentModule_ProvideActivityFactory implements Factory<Activity> {
  private final FragmentModule module;

  public FragmentModule_ProvideActivityFactory(FragmentModule module) {
    this.module = module;
  }

  @Override
  public Activity get() {
    return Preconditions.checkNotNull(
        module.provideActivity(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Activity> create(FragmentModule module) {
    return new FragmentModule_ProvideActivityFactory(module);
  }
}
