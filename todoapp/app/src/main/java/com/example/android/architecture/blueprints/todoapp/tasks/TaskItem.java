package com.example.android.architecture.blueprints.todoapp.tasks;


import com.example.android.architecture.blueprints.todoapp.data.model.Task;

import androidx.annotation.DrawableRes;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * A task that should be displayed as an item in a list of tasks.
 * Contains the task, the action that should be triggered when taping on the task, the action that
 * should be triggered when checking or unchecking a task and the background that should be used for
 * this task.
 */
final class TaskItem {

    private Task mTask;

    @DrawableRes
    private int mBackground;

    private Action mOnClickAction;

    private Consumer<Boolean> mOnCheckAction;

    public TaskItem(Task task, @DrawableRes int background,
                    Action onClickAction, Consumer<Boolean> onCheckAction) {
        mTask = task;
        mBackground = background;
        mOnClickAction = onClickAction;
        mOnCheckAction = onCheckAction;
    }

    public Task getTask() {
        return mTask;
    }

    public int getBackground() {
        return mBackground;
    }

    /**
     * @return the action to be triggered on click events
     */
    public Action getOnClickAction() {
        return mOnClickAction;
    }

    /**
     * @return the action to be triggered when the task is checked (marked as done or active)
     */
    public Consumer<Boolean> getOnCheckAction() {
        return mOnCheckAction;
    }
}
