package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.view.AddEditTaskFragment;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.view.TaskDetailFragment;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.view.TasksFragment;
import com.example.android.architecture.blueprints.todoapp.util.providers.BaseNavigator;
import com.example.android.architecture.blueprints.todoapp.util.providers.Navigator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigatorModule {

  @Provides
  /*@TasksFragmentScope*/
  @Named("tasksNavigationProvider")
  BaseNavigator provideTasksNavigationProvider(TasksFragment fragment) {
    return new Navigator(fragment.getActivity());
  }

  @Provides
  /*@TaskDetailFragmentScope*/
  @Named("taskDetailNavigationProvider")
  BaseNavigator provideTaskDetailNavigationProvider(TaskDetailFragment fragment) {
    return new Navigator(fragment.getActivity());
  }

  @Provides
  /*@AddEditTaskFragmentScope*/
  @Named("AddEditTaskNavigationProvider")
  BaseNavigator provideAddEditTaskNavigationProvider(AddEditTaskFragment fragment) {
    return new Navigator(fragment.getActivity());
  }

}
