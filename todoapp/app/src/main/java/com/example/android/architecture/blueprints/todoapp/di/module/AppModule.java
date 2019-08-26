package com.example.android.architecture.blueprints.todoapp.di.module;

import android.content.Context;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.util.providers.BaseResourceProvider;
import com.example.android.architecture.blueprints.todoapp.util.providers.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  @Provides
  Context provideContext(ToDoApplication application) {
    return application;
  }

  @Provides
  @Singleton
  BaseResourceProvider provideResourceProvider(Context context) {
    return new ResourceProvider(context);
  }
}
