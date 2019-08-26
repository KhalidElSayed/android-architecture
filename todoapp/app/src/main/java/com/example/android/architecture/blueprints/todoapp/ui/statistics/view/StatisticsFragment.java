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
package com.example.android.architecture.blueprints.todoapp.ui.statistics.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.ui.base.view.BaseFragment;
import com.example.android.architecture.blueprints.todoapp.ui.base.viewmodel.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.uimodel.StatisticsUiModel;
import com.example.android.architecture.blueprints.todoapp.ui.statistics.viewmodel.StatisticsViewModel;
import com.google.common.base.Preconditions;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Main UI for the statistics screen.
 */
public class StatisticsFragment extends BaseFragment {

    private static final String TAG = StatisticsFragment.class.getSimpleName();

    private TextView mStatisticsTV;

    @Inject
    ViewModelFactory viewModelFactory;
    @Nullable
    private StatisticsViewModel mViewModel;

    /**
     * using a CompositeSubscription to gather all the subscriptions
     * so all of them can be later unsubscribed together
     * */
    @Inject
    CompositeDisposable mDisposable;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_frag, container, false);
        mStatisticsTV = root.findViewById(R.id.statistics);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(StatisticsViewModel.class);

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

    private void bindViewModel() {
        Preconditions.checkNotNull(mViewModel);

        // The ViewModel holds an observable containing the state of the UI.
        // subscribe to the emissions of the UiModel
        // every time a new Ui Model is emitted, update the statistics
        mDisposable.add(mViewModel.getUiModel()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        this::updateStatistics,
                        // onError
                        throwable -> Log.e(TAG, "Error retrieving statistics text", throwable)));
    }

    private void unbindViewModel() {
        Preconditions.checkNotNull(mDisposable);
        // disposing from all the subscriptions to ensure we don't have any memory leaks
        mDisposable.dispose();
    }

    private void updateStatistics(@NonNull StatisticsUiModel statistics) {
        mStatisticsTV.setText(statistics.getText());
    }
}
