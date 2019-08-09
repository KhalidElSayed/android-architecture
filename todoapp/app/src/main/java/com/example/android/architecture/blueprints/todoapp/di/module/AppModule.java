package com.example.android.architecture.blueprints.todoapp.di.module;

import android.content.Context;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.util.providers.BaseResourceProvider;
import com.example.android.architecture.blueprints.todoapp.util.providers.ResourceProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

  @Binds
  abstract Context bindContext(ToDoApplication application);

  @Provides
  @Singleton
  BaseResourceProvider provideResourceProvider(Context context) {
    return new ResourceProvider(context);
  }
}
