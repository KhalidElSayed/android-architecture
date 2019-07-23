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

package com.example.android.architecture.blueprints.todoapp.data;

import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskDatabase;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource;
import com.example.android.architecture.blueprints.todoapp.util.schedulers.BaseSchedulerProvider;
import com.example.android.architecture.blueprints.todoapp.util.schedulers.ImmediateSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for the {@link TasksDataSource}, which uses the {@link TaskDatabase}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TasksLocalDataSourceTest {

    private final static String TITLE = "title";

    private final static String TITLE2 = "title2";

    private final static String TITLE3 = "title3";

    private BaseSchedulerProvider mSchedulerProvider;

    private TasksLocalDataSource mLocalDataSource;

    private Task mTask = new Task(TITLE, "");

    @Before
    public void setup() {
        TasksLocalDataSource.destroyInstance();
        mSchedulerProvider = new ImmediateSchedulerProvider();

        mLocalDataSource = TasksLocalDataSource.getInstance(
                InstrumentationRegistry.getTargetContext(), mSchedulerProvider);
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllTasks();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveTask_retrievesTask() {
        // When saved into the persistent repository
        mLocalDataSource.saveTask(mTask).subscribe();

        // Then the task can be retrieved from the persistent repository
        TestObserver<Task> testObserver = new TestObserver<>();
        mLocalDataSource.getTask(mTask.getId()).subscribe(testObserver);
        testObserver.assertValue(mTask);
    }

    @Test
    public void completeTask_retrievedTaskIsComplete() {
        // Given a new task in the persistent repository
        mLocalDataSource.saveTask(mTask).subscribe();

        // When completed in the persistent repository
        mLocalDataSource.completeTask(mTask).subscribe();

        // Then the task can be retrieved from the persistent repository and is complete
        TestObserver<Task> testObserver = new TestObserver<>();
        mLocalDataSource.getTask(mTask.getId()).subscribe(testObserver);
        testObserver.assertValueCount(1);
        Task result = testObserver.values().get(0);
        assertThat(result.isCompleted(), is(true));
    }

    @Test
    public void activateTask_retrievedTaskIsActive() {
        // Given a new completed task in the persistent repository
        mLocalDataSource.saveTask(mTask).subscribe();
        mLocalDataSource.completeTask(mTask).subscribe();

        // When activated in the persistent repository
        mLocalDataSource.activateTask(mTask).subscribe();

        // Then the task can be retrieved from the persistent repository and is active
        TestObserver<Task> testObserver = new TestObserver<>();
        mLocalDataSource.getTask(mTask.getId()).subscribe(testObserver);
        testObserver.assertValueCount(1);
        Task result = testObserver.values().get(0);
        assertThat(result.isActive(), is(true));
        assertThat(result.isCompleted(), is(false));
    }

    @Test
    public void clearCompletedTask_taskNotRetrievable() {
        // Given 2 new completed tasks and 1 active task in the persistent repository
        final Task newTask1 = new Task(TITLE, "");
        mLocalDataSource.saveTask(newTask1).subscribe();
        mLocalDataSource.completeTask(newTask1).subscribe();
        final Task newTask2 = new Task(TITLE2, "");
        mLocalDataSource.saveTask(newTask2).subscribe();
        mLocalDataSource.completeTask(newTask2).subscribe();
        final Task newTask3 = new Task(TITLE3, "");
        mLocalDataSource.saveTask(newTask3).subscribe();

        // When completed tasks are cleared in the repository
        mLocalDataSource.clearCompletedTasks();

        // Then the completed tasks cannot be retrieved and the active one can
        TestObserver<List<Task>> testObserver = new TestObserver<>();
        mLocalDataSource.getTasks().subscribe(testObserver);
        List<Task> result = testObserver.values().get(0);
        assertThat(result, not(hasItems(newTask1, newTask2)));
    }

    @Test
    public void deleteAllTasks_emptyListOfRetrievedTask() {
        // Given a new task in the persistent repository and a mocked callback
        mLocalDataSource.saveTask(mTask).subscribe();

        // When all tasks are deleted
        mLocalDataSource.deleteAllTasks();

        // Then the retrieved tasks is an empty list
        TestObserver<List<Task>> testObserver = new TestObserver<>();
        mLocalDataSource.getTasks().subscribe(testObserver);
        List<Task> result = testObserver.values().get(0);
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void getTasks_retrieveSavedTasks() {
        // Given 2 new tasks in the persistent repository
        final Task newTask1 = new Task(TITLE, "");
        mLocalDataSource.saveTask(newTask1).subscribe();
        final Task newTask2 = new Task(TITLE, "");
        mLocalDataSource.saveTask(newTask2).subscribe();

        // Then the tasks can be retrieved from the persistent repository
        TestObserver<List<Task>> testObserver = new TestObserver<>();
        mLocalDataSource.getTasks().subscribe(testObserver);
        List<Task> result = testObserver.values().get(0);
        assertThat(result, hasItems(newTask1, newTask2));
    }

    @Test
    public void getTask_whenTaskNotSaved() {
        //Given that no task has been saved
        //When querying for a task, null is returned.
        TestObserver<Task> testObserver = new TestObserver<>();
        mLocalDataSource.getTask(1).subscribe(testObserver);

        // TODO: the RxJava 2 change the mechanism of the item flow so we can't just emit an null values.
        testObserver.assertEmpty();
    }

    @Test
    public void getTask_emits_whenNewTaskSaved() {
        // Given that we are subscribed to the list of tasks
        TestObserver<List<Task>> testObserver = new TestObserver<>();
        mLocalDataSource.getTasks().subscribe(testObserver);

        // When adding a new task
        mLocalDataSource.saveTask(mTask).subscribe();

        // 2 emissions are registered
        testObserver.assertValueCount(2);
        // The first one is an empty list, because the local data source was empty
        assertThat(testObserver.values().get(0).isEmpty(), is(true));
        // The 2nd one is a list containing the added task
        List<Task> tasks = testObserver.values().get(1);
        assertThat(tasks.size(), is(1));
        assertThat(tasks.get(0), is(mTask));
    }

    @Test
    public void getTask_emits_whenTaskCompleted() {
        //Given that a task is saved
        mLocalDataSource.saveTask(mTask).subscribe();
        // Given that we are subscribed to the list of tasks
        TestObserver<List<Task>> testObserver = new TestObserver<>();
        mLocalDataSource.getTasks().subscribe(testObserver);

        // When adding a new task
        mLocalDataSource.completeTask(mTask).subscribe();

        // 2 emissions are registered
        testObserver.assertValueCount(2);
        // The first one is an empty list, because the local data source was empty
        assertThat(testObserver.values().get(0).get(0), is(mTask));
        // The 2nd one is a list containing the added task
        List<Task> tasks = testObserver.values().get(1);
        assertThat(tasks.size(), is(1));
        assertTrue(tasks.get(0).isCompleted());
    }

    @Test
    public void saveTask_replacesTask() {
        //Given that a task is saved
        mLocalDataSource.saveTask(mTask).subscribe();
        // Given a task with the same id
        Task edited = new Task("edited", "edited", mTask.getId());

        // When the task is saved
        TestObserver testObserver = new TestObserver();
        mLocalDataSource.saveTask(edited).subscribe(testObserver);

        // No error is emitted
        testObserver.assertNoErrors();
        assertTaskInLocalRepository(edited);
    }

    private void assertTaskInLocalRepository(Task task) {
        // Given that we are subscribed to the list of tasks
        TestObserver<List<Task>> testObserver = new TestObserver<>();
        mLocalDataSource.getTasks().subscribe(testObserver);

        List<Task> tasks = testObserver.values().get(0);
        assertThat(tasks.size(), is(1));
        assertEquals(tasks.get(0), task);
    }
}
