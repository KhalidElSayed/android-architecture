package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.StatisticsActivity;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.TasksActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

  @ContributesAndroidInjector
  abstract TasksActivity contributeTasksActivity();

  @ContributesAndroidInjector
  abstract TaskDetailActivity contributeTaskDetailActivity();

  @ContributesAndroidInjector
  abstract AddEditTaskActivity contributeAddEditTaskActivity();

  @ContributesAndroidInjector
  abstract StatisticsActivity contributeStatisticsActivity();
}
