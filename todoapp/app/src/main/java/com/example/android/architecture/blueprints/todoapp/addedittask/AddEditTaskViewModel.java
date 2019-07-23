package com.example.android.architecture.blueprints.todoapp.addedittask;


import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ViewModel handling the adding and deleting of tasks.
 */
public class AddEditTaskViewModel {

    @NonNull
    private final TasksRepository mTasksRepository;

    @NonNull
    private final AddEditTaskNavigator mNavigator;

    // using a PublishSubject because we are not interested in the last object that was emitted
    // before subscribing. Like this we avoid displaying the snackbar multiple times
    @NonNull
    private final PublishSubject<Integer> mSnackbarText;

    @Nullable
    private Integer mTaskId;

    @Nullable
    private String mRestoredTitle;

    @Nullable
    private String mRestoredDescription;

    public AddEditTaskViewModel(@Nullable Integer taskId, @NonNull TasksRepository tasksRepository,
                                @NonNull AddEditTaskNavigator navigator) {
        mTasksRepository = checkNotNull(tasksRepository, "TaskRepository cannot be null");
        mNavigator = checkNotNull(navigator, "navigator cannot be null");
        mTaskId = taskId;
        mSnackbarText = PublishSubject.create();
    }

    /**
     * @return a stream that emits when a snackbar should be displayed. The stream contains the
     * snackbar text
     */
    @NonNull
    public Observable<Integer> getSnackbarText() {
        return mSnackbarText.hide();
    }

    /**
     * @return a stream containing the model for the UI.
     */
    @NonNull
    public Observable<AddEditTaskUiModel> getUiModel() {
//        if (Strings.isNullOrEmpty(mTaskId)) {
        if (mTaskId == null) {
            // new task. nothing to do here.
            return Observable.empty();
        }
        return mTasksRepository
                .getTask(mTaskId)
                .map(this::restoreTask)
                .doOnError(__ -> showSnackbar(R.string.empty_task_message))
                .map(task -> new AddEditTaskUiModel(task.getTitle(), task.getDescription()));
    }

    /**
     * Sets the restored state.
     *
     * @param title       the restored title.
     * @param description the restored description.
     */
    public void setRestoredState(@Nullable String title, @Nullable String description) {
        mRestoredTitle = title;
        mRestoredDescription = description;
    }

    private Task restoreTask(Task task) {
        String title = mRestoredTitle != null ? mRestoredTitle : task.getTitle();
        String description = mRestoredDescription != null ? mRestoredDescription : task.getDescription();

        return new Task(title, description, task.getId());
    }

    /**
     * Save a task (create if it's a new task or update if task exists) with a title and a description
     *
     * @param title       title of the task
     * @param description description of the task
     */
    @NonNull
    public Completable saveTask(String title, String description) {
        return createTask(title, description)
                .doOnComplete(mNavigator::onTaskSaved);
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    private Completable createTask(String title, String description) {
        Task newTask;
        if (isNewTask()) {
            newTask = new Task(title, description);
            // TODO: this check should be moved to the view (activity)
            if (newTask.isEmpty()) {
                showSnackbar(R.string.empty_task_message);
                return Completable.complete();
            }
        } else {
            // TODO: this behaviour will be moved to the view (activity)
            newTask = new Task(title, description/*, mTaskId*/);
        }
        return mTasksRepository.saveTask(newTask);
    }

    private void showSnackbar(@StringRes int textId) {
        mSnackbarText.onNext(textId);
    }
}
