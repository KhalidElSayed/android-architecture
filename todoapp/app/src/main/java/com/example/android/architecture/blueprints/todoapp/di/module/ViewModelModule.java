package com.example.android.architecture.blueprints.todoapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.architecture.blueprints.todoapp.ui.addedittask.viewmodel.AddEditTaskViewModel;
import com.example.android.architecture.blueprints.todoapp.ui.base.viewmodel.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.viewmodel.StatisticsViewModel;
import com.example.android.architecture.blueprints.todoapp.ui.taskdetail.viewmodel.TaskDetailViewModel;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.viewmodel.TasksViewModel;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.AddEditTaskFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.StatisticsFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.TaskDetailFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.TasksFragmentScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

  /*@Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);*/

  @Binds
  @IntoMap
  /*@TasksFragmentScope*/
  @ViewModelKey(TasksViewModel.class)
  protected abstract ViewModel bindTasksViewModel(TasksViewModel tasksViewModel);

  @Binds
  @IntoMap
  /*@TaskDetailFragmentScope*/
  @ViewModelKey(TaskDetailViewModel.class)
  protected abstract ViewModel bindTaskDetailViewModel(TaskDetailViewModel taskDetailViewModel);

  @Binds
  @IntoMap
  /*@AddEditTaskFragmentScope*/
  @ViewModelKey(AddEditTaskViewModel.class)
  protected abstract ViewModel bindAddEditTaskViewModel(AddEditTaskViewModel addEditTaskViewModel);

  @Binds
  @IntoMap
  /*@StatisticsFragmentScope*/
  @ViewModelKey(StatisticsViewModel.class)
  protected abstract ViewModel bindStatisticsViewModel(StatisticsViewModel statisticsViewModel);

}
