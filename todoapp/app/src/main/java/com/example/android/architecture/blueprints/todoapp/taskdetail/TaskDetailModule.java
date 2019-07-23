package com.example.android.architecture.blueprints.todoapp.taskdetail;

import android.app.Activity;
import android.content.Context;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.util.providers.BaseNavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Enables inversion of control of the ViewModel and Navigator classes for task detail.
 */
class TaskDetailModule {

    @NonNull
    public static TaskDetailViewModel createTaskDetailsViewModel(
            @Nullable Integer taskId,
            @NonNull Activity activity) {
        Context appContext = activity.getApplicationContext();
        BaseNavigator navigationProvider = Injection.createNavigationProvider(activity);
        return new TaskDetailViewModel(taskId, Injection.provideTasksRepository(appContext),
                createTaskDetailNavigator(navigationProvider));
    }

    @NonNull
    public static TaskDetailNavigator createTaskDetailNavigator(
            @NonNull BaseNavigator navigationProvider) {
        return new TaskDetailNavigator(navigationProvider);
    }

}
