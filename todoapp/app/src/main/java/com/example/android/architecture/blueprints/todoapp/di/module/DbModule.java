package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskDatabase;
import com.example.android.architecture.blueprints.todoapp.data.source.local.dao.TaskDao;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

  /*
   * The method returns the Database object
   * */
  @Provides
  @Singleton
  TaskDatabase provideDatabase(@NonNull ToDoApplication application) {
    return Room.databaseBuilder(application, TaskDatabase.class, "Tasks.db")
            .build();
  }

  /*
   * We need the UserDao module.
   * For this, We need the UsersDatabase object
   * So we will define the providers for this here in this module.
   * */
  @Provides
  @Singleton
  TaskDao provideTaskDao(@NonNull TaskDatabase taskDatabase) {
    return taskDatabase.taskDao();
  }
}
