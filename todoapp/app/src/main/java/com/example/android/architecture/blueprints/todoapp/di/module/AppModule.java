package com.example.android.architecture.blueprints.todoapp.di.module;

import android.content.Context;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class AppModule {

  @Binds
  abstract Context bindContext(ToDoApplication application);
}
