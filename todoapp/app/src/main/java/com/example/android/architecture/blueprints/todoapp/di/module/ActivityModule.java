package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.view.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.view.StatisticsActivity;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.view.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.view.TasksActivity;

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
