package com.example.android.architecture.blueprints.todoapp.di;

import android.app.Application;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.di.module.ActivityModule;
import com.example.android.architecture.blueprints.todoapp.di.module.ApiModule;
import com.example.android.architecture.blueprints.todoapp.di.module.PrefsModule;
import com.example.android.architecture.blueprints.todoapp.di.module.RxModule;
import com.example.android.architecture.blueprints.todoapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApiModule.class, RxModule.class, ViewModelModule.class, PrefsModule.class,
        ActivityModule.class, AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<ToDoApplication> { }
}
