package com.example.android.architecture.blueprints.todoapp.di.module;

import android.app.Application;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.google.gson.Gson;

import net.grandcentrix.tray.AppPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PrefsModule {

  @Provides
  @Singleton
  @Named("prefsGson")
  Gson provideGson() {
    return new Gson();
  }

  @Provides
  @Singleton
  public AppPreferences provideAppPreferences(ToDoApplication application) {
    return new AppPreferences(application);
  }
}
