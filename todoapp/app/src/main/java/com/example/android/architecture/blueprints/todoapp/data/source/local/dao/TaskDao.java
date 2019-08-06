/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.example.android.architecture.blueprints.todoapp.data.source.local.dao;

import com.example.android.architecture.blueprints.todoapp.data.model.Task;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Data Access Object for the tasks table.
 */
@Dao
public interface TaskDao {

    /**
     * Get a task from the table.
     *
     * @param taskId the task id to be selected
     * @return the task from the table
     */
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    Flowable<Task> getTask(Integer taskId);

    /**
     * Gets all tasks from the table.
     *
     * @return all tasks as list from the table
     */
    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getTasks();

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTask(Task task);

    /**
     * Insert a list of tasks in the database. If a task already exists, replace it.
     *
     * @param tasks the tasks to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTasks(List<Task> tasks);

    /**
     * Insert a list of tasks in the database. If a task already exists, replace it.
     *
     * @param taskId the task id that would be updated
     */
    @Query("Update tasks SET completed = :isCompleted WHERE id = :taskId")
    Completable updateTask(int isCompleted, Integer taskId);

    /**
     * Delete the completed tasks.
     */
    @Query("DELETE FROM TASKS WHERE completed = :isCompleted")
    Completable deleteTask(int isCompleted);

    /**
     * Delete a task.
     */
    @Query("DELETE FROM TASKS WHERE id LIKE :taskId")
    Completable deleteTask(Integer taskId);

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM TASKS")
    Completable deleteAllTasks();
}
