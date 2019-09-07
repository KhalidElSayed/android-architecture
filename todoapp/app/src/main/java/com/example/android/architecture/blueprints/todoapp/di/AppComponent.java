package com.example.android.architecture.blueprints.todoapp.di;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.di.module.ApiModule;
import com.example.android.architecture.blueprints.todoapp.di.module.AppModule;
import com.example.android.architecture.blueprints.todoapp.di.module.DbModule;
import com.example.android.architecture.blueprints.todoapp.di.module.FragmentModule;
import com.example.android.architecture.blueprints.todoapp.di.module.PrefsModule;
import com.example.android.architecture.blueprints.todoapp.di.module.RxModule;
import com.example.android.architecture.blueprints.todoapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ApiModule.class, DbModule.class, PrefsModule.class, RxModule.class, AppModule.class,
        FragmentModule.class, ViewModelModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication> {

  @Component.Factory
  abstract class Factory implements AndroidInjector.Factory<ToDoApplication> { }
}
