package com.example.android.architecture.blueprints.todoapp.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.example.android.architecture.blueprints.todoapp.ui.base.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
