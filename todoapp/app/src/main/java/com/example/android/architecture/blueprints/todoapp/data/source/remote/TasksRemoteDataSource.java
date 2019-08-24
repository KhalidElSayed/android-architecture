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

package com.example.android.architecture.blueprints.todoapp.data.source.remote;


import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a network.
 */
@Singleton
public class TasksRemoteDataSource implements TasksDataSource {

    TodoApi todoApi;

    @Inject
    public TasksRemoteDataSource(@Named("todoApi") TodoApi api) {
        this.todoApi = api;
    }

    @Override
    public Single<List<Task>> getTasks() {
        return todoApi.getTasks();
    }

    @Override
    public Observable<Task> getTask(@NonNull Integer taskId) {
        return todoApi.getTask(taskId).toObservable();
    }

    @Override
    public Completable saveTask(@NonNull Task task) {
        checkNotNull(task);
        return todoApi.addTask(task);
    }

    @Override
    public Completable saveTasks(@NonNull List<Task> tasks) {
        return Observable.fromIterable(tasks)
                .doOnNext(this::saveTask)
                .ignoreElements();
    }

    @Override
    public Completable completeTask(@NonNull Task task) {
        checkNotNull(task);
        return completeTask(task.getId());
    }

    @Override
    public Completable completeTask(@NonNull Integer taskId) {
        Map<String, Integer> isCompleted = new HashMap<>(1);
        isCompleted.put("isCompleted", 1);
        return todoApi.updateTask(taskId, isCompleted);
    }

    @Override
    public Completable activateTask(@NonNull Task task) {
        checkNotNull(task);
        return activateTask(task.getId());
    }

    @Override
    public Completable activateTask(@NonNull Integer taskId) {
        Map<String, Integer> isCompleted = new HashMap<>(1);
        isCompleted.put("isCompleted", 0);
        return todoApi.updateTask(taskId, isCompleted);
    }

    @Override
    public Completable clearCompletedTasks() {
        return todoApi.deleteCompletedTasks();
    }

    @Override
    public Completable refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
        return Completable.complete();
    }

    @Override
    public Completable deleteTask(@NonNull Integer taskId) {
        return todoApi.deleteTask(taskId);
    }

    @Override
    public Completable deleteAllTasks() {
        return todoApi.deleteTasks();
    }
}
