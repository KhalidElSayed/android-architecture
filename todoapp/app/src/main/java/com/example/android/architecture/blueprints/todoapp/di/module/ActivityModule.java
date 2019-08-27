package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.view.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.view.StatisticsActivity;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.view.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.view.TasksActivity;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.AddEditTaskScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.StatisticsScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.TaskDetailScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.TasksScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

  @TasksScope
  @ContributesAndroidInjector(modules = {FragmentModule.class, ViewModelModule.class, NavigatorModule.class})
  abstract TasksActivity contributeTasksActivity();

  @TaskDetailScope
  @ContributesAndroidInjector(modules = {FragmentModule.class, ViewModelModule.class, NavigatorModule.class})
  abstract TaskDetailActivity contributeTaskDetailActivity();

  @AddEditTaskScope
  @ContributesAndroidInjector(modules = {FragmentModule.class, ViewModelModule.class, NavigatorModule.class})
  abstract AddEditTaskActivity contributeAddEditTaskActivity();

  @StatisticsScope
  @ContributesAndroidInjector(modules = {FragmentModule.class, ViewModelModule.class, NavigatorModule.class})
  abstract StatisticsActivity contributeStatisticsActivity();
}
