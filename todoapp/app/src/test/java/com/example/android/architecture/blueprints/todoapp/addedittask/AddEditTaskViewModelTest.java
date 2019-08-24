package com.example.android.architecture.blueprints.todoapp.addedittask;

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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link AddEditTaskViewModel}.
 */
public class AddEditTaskViewModelTest {

    private static final String NEW_TITLE = "new title";
    private static final String NEW_DESCRIPTION = "new description";
    private static final Task TASK = new Task("TITLE", "DESCRIPTION", 1);

    @Mock
    private TasksRepository mTasksRepository;

    @Mock
    private AddEditTaskNavigator mNavigator;

    private TestObserver<AddEditTaskUiModel> mTaskTestSubscriber;

    private TestObserver<Integer> mSnackbarTestSubscriber;

    private TestObserver<Void> mCompletableTestSubscriber;

    private AddEditTaskViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mTaskTestSubscriber = new TestObserver<>();
        mSnackbarTestSubscriber = new TestObserver<>();
        mCompletableTestSubscriber = new TestObserver<>();
    }

    @Test
    public void getTask_withNullTaskId_doesNotEmit() {
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);

        mViewModel.getUiModel(null).subscribe(mTaskTestSubscriber);

        mTaskTestSubscriber.assertNoValues();
    }

    @Test
    public void getTask_withEmptyTaskId_doesNotEmit() {
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);

        mViewModel.getUiModel(Integer.getInteger("-1")).subscribe(mTaskTestSubscriber);

        mTaskTestSubscriber.assertNoValues();
    }

    @Test
    public void getTask_returnsCorrectData() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK.getId())).thenReturn(Observable.just(TASK));
        // Get a reference to the class under test for a task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);

        // When subscribing to the task
        mViewModel.getUiModel(TASK.getId()).subscribe(mTaskTestSubscriber);

        // The correct task is emitted
        AddEditTaskUiModel model = mTaskTestSubscriber.values().get(0);
        assertEquals(model.getTitle(), TASK.getTitle());
        assertEquals(model.getDescription(), TASK.getDescription());
    }

    @Test
    public void getTask_whenRepositoryReturnsError_snackbarEmits() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK.getId())).thenReturn(Observable.error(new Exception()));
        // Get a reference to the class under test for a task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        // With subscribed to the snackbar
        mViewModel.getSnackbarText().subscribe(mSnackbarTestSubscriber);

        // When subscribing to the task
        mViewModel.getUiModel(TASK.getId()).subscribe(mTaskTestSubscriber);

        // The correct resource id is emitted
        mSnackbarTestSubscriber.assertValue(R.string.empty_task_message);
    }

    @Test
    public void saveTask_emitsError_whenErrorSavingInRepository() {
        // Get a reference to the class under test for a null task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        // Given that the repository returns an error when a task is saved
        when(mTasksRepository.saveTask(any(Task.class)))
                .thenReturn(Completable.error(new RuntimeException()));

        // When saving a task
        mViewModel.saveTask(null, NEW_TITLE, NEW_DESCRIPTION).subscribe(mCompletableTestSubscriber);

        // An error is emitted
        mCompletableTestSubscriber.assertError(RuntimeException.class);
    }

    @Test
    public void saveTask_whenNoTask_createsTask() {
        // Get a reference to the class under test for a null task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        withTaskInRepositorySavedSuccessfully();

        // When saving a task
        mViewModel.saveTask(null, NEW_TITLE, NEW_DESCRIPTION).subscribe();

        // The task is saved in repository
        verify(mTasksRepository).saveTask(any(Task.class));
    }

    @Test
    public void saveTask_whenNoTask_navigates() {
        // Get a reference to the class under test for a null task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        withTaskInRepositorySavedSuccessfully();

        // When saving a task
        mViewModel.saveTask(null, NEW_TITLE, NEW_DESCRIPTION).subscribe();

        // Navigation is triggered
        verify(mNavigator).onTaskSaved();
    }

    @Test
    public void saveTask_withEmptyTitleAndDescription_doesntSaveTask() {
        // Get a reference to the class under test for a null task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);

        // When saving an invalid task
        mViewModel.saveTask(null, "", "");

        // The invalid task is not saved in repository
        verify(mTasksRepository, never()).saveTask(any(Task.class));
    }

    @Test
    public void saveTask_withEmptyTitleAndDescription_triggersSnackbar() {
        // Get a reference to the class under test for a null task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        // With subscribed to snackbar text
        mViewModel.getSnackbarText().subscribe(mSnackbarTestSubscriber);

        // When saving an invalid task
        mViewModel.saveTask(null, "", "").subscribe();

        // The snackbar text emits with empty message
        mSnackbarTestSubscriber.assertValue(R.string.empty_task_message);
    }

    @Test
    public void saveTask_withExistingTaskId_savesTask() {
        // Get a reference to the class under test for a task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        withTaskInRepositorySavedSuccessfully();

        // When saving the task with new title and new description
        mViewModel.saveTask(TASK.getId(), NEW_TITLE, NEW_DESCRIPTION).subscribe();

        // The updated task is saved in the repository
        Task expected = new Task(NEW_TITLE, NEW_DESCRIPTION, TASK.getId());
        verify(mTasksRepository).saveTask(expected);
    }

    @Test
    public void saveTask_withExistingTaskId_navigates() {
        // Get a reference to the class under test for a task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);
        withTaskInRepositorySavedSuccessfully();

        // When saving the task with new title and new description
        mViewModel.saveTask(TASK.getId(), NEW_TITLE, NEW_DESCRIPTION).subscribe(mCompletableTestSubscriber);

        // The navigation is triggered
        verify(mNavigator).onTaskSaved();
        // The completable completes
        mCompletableTestSubscriber.assertComplete();
    }

    @Test
    public void restoreTask_withTitleUpdated() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK.getId())).thenReturn(Observable.just(TASK));
        // Get a reference to the class under test for a task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);

        // When setting a restore title
        mViewModel.setRestoredState(NEW_TITLE, null);
        // When that we are subscribing to the task
        mViewModel.getUiModel(TASK.getId()).subscribe(mTaskTestSubscriber);

        // The emitted UI model has the restored title
        AddEditTaskUiModel model = mTaskTestSubscriber.values().get(0);
        assertEquals(model.getTitle(), NEW_TITLE);
        assertEquals(model.getDescription(), TASK.getDescription());
    }

    @Test
    public void restoreTask_withDescriptionUpdated() {
        // Given a task in the repository
        when(mTasksRepository.getTask(TASK.getId())).thenReturn(Observable.just(TASK));
        // Get a reference to the class under test for a task id
        mViewModel = new AddEditTaskViewModel(mTasksRepository, mNavigator);

        // When setting a restore description
        mViewModel.setRestoredState(null, NEW_DESCRIPTION);
        // When that we are subscribing to the task
        mViewModel.getUiModel(TASK.getId()).subscribe(mTaskTestSubscriber);

        // The emitted UI model has the restored description
        AddEditTaskUiModel model = mTaskTestSubscriber.values().get(0);
        assertEquals(model.getDescription(), NEW_DESCRIPTION);
        assertEquals(model.getTitle(), TASK.getTitle());
    }

    private void withTaskInRepositorySavedSuccessfully() {
        when(mTasksRepository.saveTask(any(Task.class))).thenReturn(Completable.complete());
    }
}
