package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsActivity;
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity;

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
