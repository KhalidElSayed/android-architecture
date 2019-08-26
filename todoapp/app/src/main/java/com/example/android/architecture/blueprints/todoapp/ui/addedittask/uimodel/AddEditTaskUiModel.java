package com.example.android.architecture.blueprints.todoapp.ui.addedittask.uimodel;

/**
 * Model for the Add/Edit task screen.
 */
public class AddEditTaskUiModel {

    private final String mTitle;

    private final String mDescription;

    public AddEditTaskUiModel(String mTitle, String mDescription) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
