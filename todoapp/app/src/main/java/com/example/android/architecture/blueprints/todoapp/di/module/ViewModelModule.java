package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.di.ViewModelKey;
import com.example.android.architecture.blueprints.todoapp.ui.addedittask.AddEditTaskViewModel;
import com.example.android.architecture.blueprints.todoapp.ui.base.viewmodel.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.StatisticsViewModel;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.TaskDetailViewModel;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.TasksViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
  protected abstract ViewModel bindTasksViewModel(TasksViewModel splashViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(AddEditTaskViewModel.class)
  protected abstract ViewModel bindAddEditTaskViewModel(AddEditTaskViewModel observationViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(TaskDetailViewModel.class)
  protected abstract ViewModel bindTaskDetailViewModel(TaskDetailViewModel monitorViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(StatisticsViewModel.class)
  protected abstract ViewModel bindStatisticsViewModel(StatisticsViewModel searchViewModel);

}
