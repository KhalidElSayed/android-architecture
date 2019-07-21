package com.example.android.architecture.blueprints.todoapp.tasks;


import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * The string and image that should be displayed when there are no tasks.
 */
final class NoTasksModel {

    @StringRes
    private int mText;

    @DrawableRes
    private int mIcon;

    private boolean mIsAddNewTaskVisible;

    public NoTasksModel(int text, int icon, boolean isAddNewTaskVisible) {
        mText = text;
        mIcon = icon;
        mIsAddNewTaskVisible = isAddNewTaskVisible;
    }

    @StringRes
    public int getText() {
        return mText;
    }

    @DrawableRes
    public int getIcon() {
        return mIcon;
    }

    public boolean isAddNewTaskVisible() {
        return mIsAddNewTaskVisible;
    }
}
