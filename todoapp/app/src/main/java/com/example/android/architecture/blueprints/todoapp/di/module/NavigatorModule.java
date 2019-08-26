package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.TasksActivity;
import com.example.android.architecture.blueprints.todoapp.util.providers.BaseNavigator;
import com.example.android.architecture.blueprints.todoapp.util.providers.Navigator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigatorModule {

  @Provides
  @Named("tasksNavigationProvider")
  BaseNavigator provideTasksNavigationProvider(TasksActivity activity) {
    return new Navigator(activity);
  }

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
