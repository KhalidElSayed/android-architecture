package com.example.android.architecture.blueprints.todoapp.di;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.di.module.ApiModule;
import com.example.android.architecture.blueprints.todoapp.di.module.AppModule;
import com.example.android.architecture.blueprints.todoapp.di.module.DbModule;
import com.example.android.architecture.blueprints.todoapp.di.module.FragmentModule;
import com.example.android.architecture.blueprints.todoapp.di.module.NavigatorModule;
import com.example.android.architecture.blueprints.todoapp.di.module.PrefsModule;
import com.example.android.architecture.blueprints.todoapp.di.module.RxModule;
import com.example.android.architecture.blueprints.todoapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApiModule.class, DbModule.class, RxModule.class, PrefsModule.class,
        FragmentModule.class, NavigatorModule.class, ViewModelModule.class, AppModule.class, AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication> {

  /*@Component.Builder
  abstract class Builder extends AndroidInjector.Builder<ToDoApplication> { }*/

  @Component.Factory
  abstract class Factory implements AndroidInjector.Factory<ToDoApplication> { }
}
