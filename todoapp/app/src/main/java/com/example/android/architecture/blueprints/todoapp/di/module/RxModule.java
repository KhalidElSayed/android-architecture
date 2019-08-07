package com.example.android.architecture.blueprints.todoapp.di.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class RxModule {

  @Provides
  CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}
