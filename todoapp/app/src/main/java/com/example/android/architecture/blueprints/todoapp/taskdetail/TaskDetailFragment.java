/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.example.android.architecture.blueprints.todoapp.taskdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.base.view.BaseFragment;
import com.example.android.architecture.blueprints.todoapp.base.viewmodel.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Main UI for the task detail screen.
 */
public class TaskDetailFragment extends BaseFragment {

    private static final String TAG = TaskDetailFragment.class.getSimpleName();

    @NonNull
    private static final String ARGUMENT_TASK_ID = "TASK_ID";

    private TextView mLoadingProgress;

    private TextView mDetailTitle;

    private TextView mDetailDescription;

    private CheckBox mDetailCompleteStatus;

    @Inject
    ViewModelFactory viewModelFactory;
    @Nullable
    private TaskDetailViewModel mViewModel;

    /**
     * using a CompositeSubscription to gather all the subscriptions, so all of them can be
     * later unsubscribed together
     * */
    @Inject
    private CompositeDisposable mDisposable;

    public static TaskDetailFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);
        setHasOptionsMenu(true);
        mLoadingProgress = root.findViewById(R.id.loading_progress);
        mDetailTitle = root.findViewById(R.id.task_detail_title);
        mDetailDescription = root.findViewById(R.id.task_detail_description);
        mDetailCompleteStatus = root.findViewById(R.id.task_detail_complete);

        setupFab();

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskDetailViewModel.class);

        return root;
    }

    @Override
    public void onResume() {
        bindViewModel();
        super.onResume();
    }

    @Override
    public void onPause() {
        unbindViewModel();
        super.onPause();
    }

    private void setupFab() {
        FloatingActionButton fab =
                getActivity().findViewById(R.id.fab_edit_task);

        fab.setOnClickListener(__ -> editTask(getTaskId()));
    }

    private void bindViewModel() {
        // subscribe to the emissions of the Ui Model
        // every time a new Ui Model, update the View
        mDisposable.add(mViewModel.getTaskUiModel(getTaskId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        this::updateView,
                        // onError
                        __ -> showMissingTask()));

        // The ViewModel holds an observable containing the state of the UI.
        // subscribe to the emissions of the loading indicator visibility
        // for every emission, update the visibility of the loading indicator
        mDisposable.add(mViewModel.getLoadingIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        this::setLoadingIndicatorVisibility,
                        // onError
                        __ -> showMissingTask()));

        // subscribe to the emissions of the snackbar text
        // every time the snackbar text emits, show the snackbar
        mDisposable.add(mViewModel.getSnackbarText()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        this::showSnackbar,
                        // onError
                        throwable -> Log.e(TAG, "Unable to display snackbar text", throwable)));
    }

    private void unbindViewModel() {
        // disposing from all the subscriptions to ensure we don't have any memory leaks
        mDisposable.dispose();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteTask(getTaskId());
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateView(TaskUiModel model) {
        int titleVisibility = model.isShowTitle() ? View.VISIBLE : View.GONE;
        int descriptionVisibility = model.isShowDescription() ? View.VISIBLE : View.GONE;

        mDetailTitle.setVisibility(titleVisibility);
        mDetailTitle.setText(model.getTitle());
        mDetailDescription.setVisibility(descriptionVisibility);
        mDetailDescription.setText(model.getDescription());
        showCompletionStatus(model.isChecked());
    }

    private void setLoadingIndicatorVisibility(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        mLoadingProgress.setVisibility(visibility);
    }

    private void showCompletionStatus(final boolean complete) {
        mDetailCompleteStatus.setChecked(complete);
        mDetailCompleteStatus.setOnCheckedChangeListener(
                (buttonView, isChecked) -> taskCheckChanged(getTaskId(), isChecked));
    }

    private void taskCheckChanged(@Nullable Integer taskId, final boolean checked) {
        mDisposable.add(mViewModel.taskCheckChanged(taskId, checked)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        () -> {
                            // nothing to do here
                        },
                        // onError
                        throwable -> showMissingTask()));
    }

    private void deleteTask(@Nullable Integer taskId) {
        mDisposable.add(mViewModel.deleteTask(taskId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        () -> {
                            //nothing to do here
                        },
                        // onError
                        __ -> showMissingTask()));
    }

    private void editTask(@Nullable Integer taskId) {
        mDisposable.add(mViewModel.editTask(taskId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(  // onNext
                        () -> {
                            //nothing to do here
                        },
                        // onError
                        __ -> showMissingTask()));
    }

    private void showSnackbar(@StringRes int text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.handleActivityResult(requestCode, resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showMissingTask() {
        mDetailTitle.setText("");
        mDetailDescription.setText(getString(R.string.no_data));
    }

    @Nullable
    private Integer getTaskId() {
        if (getArguments() != null) {
            return getArguments().getInt(ARGUMENT_TASK_ID);
        }
        return null;
    }
}
