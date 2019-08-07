package com.example.android.architecture.blueprints.todoapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskViewModel;
import com.example.android.architecture.blueprints.todoapp.base.viewmodel.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.di.ViewModelKey;
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsViewModel;
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailViewModel;
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

  @Binds
  @IntoMap
  @ViewModelKey(TasksViewModel.class)
  protected abstract ViewModel bindSplashViewModel(TasksViewModel splashViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(AddEditTaskViewModel.class)
  protected abstract ViewModel bindObservationViewModel(AddEditTaskViewModel observationViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(TaskDetailViewModel.class)
  protected abstract ViewModel bindMonitorViewModel(TaskDetailViewModel monitorViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(StatisticsViewModel.class)
  protected abstract ViewModel bindSearchViewModel(StatisticsViewModel searchViewModel);

}
