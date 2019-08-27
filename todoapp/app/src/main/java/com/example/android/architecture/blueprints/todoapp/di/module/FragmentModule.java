package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.view.AddEditTaskFragment;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.view.StatisticsFragment;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.view.TaskDetailFragment;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.view.TasksFragment;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.AddEditTaskFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.StatisticsFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.TaskDetailFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.TasksFragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

  /*@TasksFragmentScope*/
  @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
  abstract TasksFragment contributeTasksFragment();

  /*@TaskDetailFragmentScope*/
  @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
  abstract TaskDetailFragment contributeTaskDetailFragment();

  /*@AddEditTaskFragmentScope*/
  @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
  abstract AddEditTaskFragment contributeAddEditTaskFragment();

  /*@StatisticsFragmentScope*/
  @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
  abstract StatisticsFragment contributeStatisticsFragment();
}
