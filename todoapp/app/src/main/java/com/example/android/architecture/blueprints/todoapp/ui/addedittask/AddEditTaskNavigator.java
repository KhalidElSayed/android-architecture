package com.example.android.architecture.blueprints.todoapp.ui.addedittask;

import android.app.Activity;

import com.example.android.architecture.blueprints.todoapp.util.providers.BaseNavigator;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;

/**
 * Defines the navigation actions that can be called from AddEditTask screen
 */
public class AddEditTaskNavigator {

    @NonNull
    private final BaseNavigator mNavigationProvider;

    @Inject
    public AddEditTaskNavigator(@NonNull @Named("AddEditTaskNavigationProvider") BaseNavigator navigationProvider) {
        mNavigationProvider = navigationProvider;
    }

    /**
     * When the task was saved, the activity should finish with success.
     */
    public void onTaskSaved() {
        mNavigationProvider.finishActivityWithResult(Activity.RESULT_OK);
    }
}
