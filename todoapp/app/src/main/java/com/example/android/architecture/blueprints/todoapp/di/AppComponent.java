package com.example.android.architecture.blueprints.todoapp.di;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.di.module.ActivityModule;
import com.example.android.architecture.blueprints.todoapp.di.module.ApiModule;
import com.example.android.architecture.blueprints.todoapp.di.module.AppModule;
import com.example.android.architecture.blueprints.todoapp.di.module.DbModule;
import com.example.android.architecture.blueprints.todoapp.di.module.NavigatorModule;
import com.example.android.architecture.blueprints.todoapp.di.module.PrefsModule;
import com.example.android.architecture.blueprints.todoapp.di.module.RxModule;
import com.example.android.architecture.blueprints.todoapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApiModule.class, DbModule.class, RxModule.class, ViewModelModule.class,
        PrefsModule.class, ActivityModule.class, NavigatorModule.class, AppModule.class, AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<ToDoApplication> { }
}
