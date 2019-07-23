/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.content.Context;

import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.local.dao.TaskDao;
import com.example.android.architecture.blueprints.todoapp.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class TasksLocalDataSource implements TasksDataSource {

    @Nullable
    private static TasksLocalDataSource INSTANCE;

    @NonNull
    private final TaskDao mTaskDao;

    // Prevent direct instantiation.
    private TasksLocalDataSource(@NonNull Context context,
                                 @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        mTaskDao = Room.databaseBuilder(context, TaskDatabase.class, "Tasks.db")
                .fallbackToDestructiveMigration()
//                .addMigrations(TaskDatabase.MIGRATION_1_2)
                .build()
                .taskDao();
    }

    public static TasksLocalDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * @return an Observable that emits the list of tasks in the database, every time the Tasks
     * table is modified
     */
    @Override
    public Single<List<Task>> getTasks() {
        return mTaskDao.getTasks().firstOrError();
    }

    @Override
    public Observable<Task> getTask(@NonNull Integer taskId) {
        return mTaskDao.getTask(taskId).toObservable();
    }

    @Override
    public Completable saveTask(@NonNull Task task) {
        checkNotNull(task);
        return mTaskDao.insertTask(task);
    }

    @Override
    public Completable saveTasks(@NonNull List<Task> tasks) {
        checkNotNull(tasks);
        return mTaskDao.insertTasks(tasks);
    }

    @Override
    public Completable completeTask(@NonNull Task task) {
        checkNotNull(task);
        return completeTask(task.getId());
    }

    @Override
    public Completable completeTask(@NonNull Integer taskId) {
        /*return Completable.fromAction(() -> {
            ContentValues values = new ContentValues();
            values.put(TaskEntry.COLUMN_NAME_COMPLETED, true);

            String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {taskId};
//            mDatabaseHelper.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs);
        });*/
        return mTaskDao.updateTask(1, taskId);
    }

    @Override
    public Completable activateTask(@NonNull Task task) {
        return activateTask(task.getId());
    }

    @Override
    public Completable activateTask(@NonNull Integer taskId) {
        /*return Completable.fromAction(() -> {
            ContentValues values = new ContentValues();
            values.put(TaskEntry.COLUMN_NAME_COMPLETED, false);

            String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {taskId};
//            mDatabaseHelper.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs);
        });*/
        return mTaskDao.updateTask(0, taskId);
    }

    @Override
    public void clearCompletedTasks() {
        /*String selection = TaskEntry.COLUMN_NAME_COMPLETED + " LIKE ?";
        String[] selectionArgs = {"1"};
        mDatabaseHelper.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);*/
        mTaskDao.deleteTask(1);
    }

    @Override
    public Completable refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
        return Completable.complete();
    }

    @Override
    public void deleteTask(@NonNull Integer taskId) {
        /*String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {taskId};
        mDatabaseHelper.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);*/
        mTaskDao.deleteTask(taskId);
    }

    @Override
    public void deleteAllTasks() {
        mTaskDao.deleteAllTasks();
//        mDatabaseHelper.delete(TaskEntry.TABLE_NAME, null);
    }
}
