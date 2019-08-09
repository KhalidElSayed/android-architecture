package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.util.providers.BaseNavigator;
import com.example.android.architecture.blueprints.todoapp.util.providers.Navigator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigatorModule {

  @Provides
  @Named("taskDetailNavigationProvider")
  BaseNavigator provideTaskDetailNavigationProvider(TaskDetailActivity activity) {
    return new Navigator(activity);
  }

  @Provides
  @Named("AddEditTaskNavigationProvider")
  BaseNavigator provideAddEditTaskNavigationProvider(AddEditTaskActivity activity) {
    return new Navigator(activity);
  }

}
