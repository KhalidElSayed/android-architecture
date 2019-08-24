package com.example.android.architecture.blueprints.todoapp.taskdetail;

import android.app.Activity;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link TaskDetailViewModel}
 */
public class TaskDetailViewModelTest {

    private static final Task TASK_WITH_TITLE_DESCRIPTION = new Task("TITLE", "DESCRIPTION", 1);
    private static final Task TASK_WITH_TITLE_COMPLETED = new Task("TITLE", "", 2, true);

    @Mock
    private TasksRepository mTasksRepository;

    @Mock
    private TaskDetailNavigator mNavigator;

    private TestObserver<Void> mTestSubscriber;

    private TestObserver<TaskUiModel> mTaskTestSubscriber;

    private TestObserver<Integer> mSnackbarTestSubscriber;

    private TaskDetailViewModel mViewModel;

    @Before
    public void setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mTestSubscriber = new TestObserver<>();
        mTaskTestSubscriber = new TestObserver<>();
        mSnackbarTestSubscriber = new TestObserver<>();
    }

    @Test
    public void getLoadingIndicator_initiallyEmitsFalse() {
        // Get a reference to the class under test
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        //When subscribing to the loading indicator
        TestObserver<Boolean> testSubscriber = new TestObserver<>();
        mViewModel.getLoadingIndicatorVisibility().subscribe(testSubscriber);

        // Emits false, since the loading is not in progress
        testSubscriber.assertValue(false);
    }

    @Test
    public void getTask_withInvalidTaskId() {
        // Get a reference to the class under test with an invalid task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the task
        mViewModel.getTaskUiModel(null).subscribe(mTaskTestSubscriber);

        // An error is emitted
        mTaskTestSubscriber.assertError(Exception.class);
    }

    @Test
    public void getTask_withTitleAndDescription_returnsCorrectData() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK_WITH_TITLE_DESCRIPTION.getId()))
                .thenReturn(Observable.just(TASK_WITH_TITLE_DESCRIPTION));
        // Get a reference to the class under test for the same task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the task
        mViewModel.getTaskUiModel(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe(mTaskTestSubscriber);

        // The correct task is emitted
        TaskUiModel taskUiModel = mTaskTestSubscriber.values().get(0);
        assertTaskWithTitleAndDescription(taskUiModel);
    }

    @Test
    public void getTask_withTitleAndCompleted_returnsCorrectData() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK_WITH_TITLE_DESCRIPTION.getId()))
                .thenReturn(Observable.just(TASK_WITH_TITLE_COMPLETED));
        // Get a reference to the class under test for the same task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the task
        mViewModel.getTaskUiModel(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe(mTaskTestSubscriber);

        // The correct task is emitted
        TaskUiModel taskUiModel = mTaskTestSubscriber.values().get(0);
        assertTaskWithTitleAndCompleted(taskUiModel);
    }

    @Test
    public void getTask_setsLoadingIndicatorStates() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK_WITH_TITLE_DESCRIPTION.getId())).thenReturn(Observable.just(TASK_WITH_TITLE_DESCRIPTION));
        // Get a reference to the class under test for the same task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        //When subscribing to the loading indicator updates
        TestObserver<Boolean> testSubscriber = new TestObserver<>();
        mViewModel.getLoadingIndicatorVisibility().subscribe(testSubscriber);
        // When subscribing to the task
        mViewModel.getTaskUiModel(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe();

        // The loading indicator emits initial values, then when the loading is in progress and when
        // loading is done
        testSubscriber.assertValues(false, true, false);
    }

    @Test
    public void editTask_withInvalidTaskId() {
        // Get a reference to the class under test with an invalid task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the editing of the task
        TestObserver<String> testSubscriber = new TestObserver<>();
        mViewModel.editTask(null).subscribe(testSubscriber);

        // An error is emitted
        testSubscriber.assertError(RuntimeException.class);
    }

    @Test
    public void editTask_returnsCorrectData() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the editing of the task
        mViewModel.editTask(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe(mTestSubscriber);

        // The Completable completes
        mTestSubscriber.assertComplete();
    }

    @Test
    public void editTask_navigates() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the editing of the task
        mViewModel.editTask(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe(mTestSubscriber);

        // Navigator handles starting to edit the task
        verify(mNavigator).onStartEditTask(TASK_WITH_TITLE_DESCRIPTION.getId());
    }

    @Test
    public void handleActivityResult_withResultOK_navigates() {
        // Get a reference to the class under test with an invalid task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        //When handling activity result for edit task
        mViewModel.handleActivityResult(TaskDetailActivity.REQUEST_EDIT_TASK, Activity.RESULT_OK);

        // The navigator is called to handle the task edited
        verify(mNavigator).onTaskEdited();
    }

    @Test
    public void handleActivityResult_withCancel_doesntNavigate() {
        // Get a reference to the class under test with an invalid task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        //When handling activity result for edit task
        mViewModel.handleActivityResult(TaskDetailActivity.REQUEST_EDIT_TASK, Activity.RESULT_CANCELED);

        // The navigator is not called to handle the task edited
        verify(mNavigator, never()).onTaskEdited();
    }

    @Test
    public void deleteTask_withInvalidTaskId() {
        // Get a reference to the class under test with an invalid task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the deletion of the task
        mViewModel.deleteTask(null).subscribe(mTestSubscriber);

        // An error is emitted
        mTestSubscriber.assertError(RuntimeException.class);
    }

    @Test
    public void deleteTask_deletesTask() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the deletion of the task
        mViewModel.deleteTask(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe(mTestSubscriber);

        // The task is deleted in the repository
        verify(mTasksRepository).deleteTask(eq(TASK_WITH_TITLE_DESCRIPTION.getId()));
        // The stream completes
        mTestSubscriber.assertComplete();
    }


    @Test
    public void deleteTask_navigates() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the deletion of the task
        mViewModel.deleteTask(TASK_WITH_TITLE_DESCRIPTION.getId()).subscribe(mTestSubscriber);

        // The navigator is called
        verify(mNavigator).onTaskDeleted();
    }

    @Test
    public void taskCheckChanged_withInvalidTaskId() {
        // Get a reference to the class under test with an invalid task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);

        // When subscribing to the task changed observer
        mViewModel.taskCheckChanged(null, true).subscribe(mTestSubscriber);

        // An error is emitted
        mTestSubscriber.assertError(RuntimeException.class);
    }

    @Test
    public void taskCheckChanged_true_completesTask() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);
        withTaskCompleted(TASK_WITH_TITLE_DESCRIPTION.getId());

        // When subscribing to the task changed observer
        mViewModel.taskCheckChanged(TASK_WITH_TITLE_DESCRIPTION.getId(), true).subscribe(mTestSubscriber);

        // The task is completed in the repository
        verify(mTasksRepository).completeTask(eq(TASK_WITH_TITLE_DESCRIPTION.getId()));
        // The stream completes
        mTestSubscriber.assertComplete();
    }

    @Test
    public void taskCheckChanged_false_activatesTask() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);
        withTaskActivated(TASK_WITH_TITLE_DESCRIPTION.getId());

        // When subscribing to the task changed observer
        mViewModel.taskCheckChanged(TASK_WITH_TITLE_DESCRIPTION.getId(), false).subscribe(mTestSubscriber);

        // The task is activated in the repository
        verify(mTasksRepository).activateTask(eq(TASK_WITH_TITLE_DESCRIPTION.getId()));
        // The stream completes
        mTestSubscriber.assertComplete();
    }

    @Test
    public void taskCheckChanged_true_completesTask_showsSnackbarText() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);
        withTaskCompleted(TASK_WITH_TITLE_DESCRIPTION.getId());

        // When subscribed to the snackbar text emissions
        mViewModel.getSnackbarText().subscribe(mSnackbarTestSubscriber);
        // and a task is marked as completed
        mViewModel.taskCheckChanged(TASK_WITH_TITLE_DESCRIPTION.getId(), true).subscribe();

        // A "Completed" snackbar text is emitted
        mSnackbarTestSubscriber.assertValue(R.string.task_marked_complete);
    }

    @Test
    public void taskCheckChanged_false_activatesTask_showsSnackbarText() {
        // Get a reference to the class under test for a task id
        mViewModel = new TaskDetailViewModel(mTasksRepository, mNavigator);
        withTaskActivated(TASK_WITH_TITLE_DESCRIPTION.getId());

        // When subscribed to the snackbar text emissions
        mViewModel.getSnackbarText().subscribe(mSnackbarTestSubscriber);
        // and a task is marked as active
        mViewModel.taskCheckChanged(TASK_WITH_TITLE_DESCRIPTION.getId(), false).subscribe();

        // An "Active" snackbar text is emitted
        mSnackbarTestSubscriber.assertValue(R.string.task_marked_active);
    }

    private void assertTaskWithTitleAndDescription(TaskUiModel model) {
        assertEquals(model.getTitle(), TASK_WITH_TITLE_DESCRIPTION.getTitle());
        assertTrue(model.isShowTitle());
        assertEquals(model.getDescription(), TASK_WITH_TITLE_DESCRIPTION.getDescription());
        assertTrue(model.isShowDescription());
        assertFalse(model.isChecked());
    }

    private void assertTaskWithTitleAndCompleted(TaskUiModel model) {
        assertEquals(model.getTitle(), TASK_WITH_TITLE_COMPLETED.getTitle());
        assertTrue(model.isShowTitle());
        assertEquals(model.getDescription(), TASK_WITH_TITLE_COMPLETED.getDescription());
        assertFalse(model.isShowDescription());
        assertTrue(model.isChecked());
    }

    private void withTaskCompleted(int task) {
        when(mTasksRepository.completeTask(task)).thenReturn(Completable.complete());
    }
    private void withTaskActivated(int task) {
        when(mTasksRepository.activateTask(task)).thenReturn(Completable.complete());
    }
}
