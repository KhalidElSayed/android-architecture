package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.view.AddEditTaskFragment;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.view.StatisticsFragment;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.view.TaskDetailFragment;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.view.TasksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

  @ContributesAndroidInjector(modules = NavigatorModule.class)
  abstract TasksFragment contributeTasksFragment();

  @ContributesAndroidInjector(modules = NavigatorModule.class)
  abstract TaskDetailFragment contributeTaskDetailFragment();

  @ContributesAndroidInjector(modules = NavigatorModule.class)
  abstract AddEditTaskFragment contributeAddEditTaskFragment();

  @ContributesAndroidInjector
  abstract StatisticsFragment contributeStatisticsFragment();
}
