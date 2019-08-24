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

package com.example.android.architecture.blueprints.todoapp.data.model;


import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * mutable model class for a Task.
 */
@Entity(tableName = "tasks")
public final class Task {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer mId;

    @Nullable
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String mDescription;

    @ColumnInfo(name = "completed")
    @SerializedName("completed")
    private boolean mCompleted;

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title       title of the task
     * @param description description of the task
     */
    public Task(@Nullable String title, @Nullable String description) {
        this(title, description, null, false);
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @NonNull Integer id) {
        this(title, description, id, false);
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this(title, description, new Random().nextInt(10), completed);
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     * @param completed   true if the task is completed, false if it's active
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description,
                @NonNull Integer id, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    @NonNull
    public Integer getId() {
        return mId;
    }
    public void setId(@NonNull Integer id) {
        this.mId = id;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(@Nullable String title) {
        this.mTitle = title;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }
    public void setDescription(@Nullable String description) {
        this.mDescription = description;
    }

    public void setCompleted(boolean completed) {
        this.mCompleted = completed;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
                Strings.isNullOrEmpty(mDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(mId, task.mId) &&
                Objects.equal(mTitle, task.mTitle) &&
                Objects.equal(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
