package com.example.android.architecture.blueprints.todoapp.ui.base.view;


import androidx.annotation.StringRes;

public interface BaseView {

    void onShowLoader();

    void onHideLoader();

    boolean isLoaderShown();

    void onShowToast(@StringRes int msgId);

    void onShowToast(String msg);

    void onShowErrorMessage(String msg);

    void onShowErrorMessage(@StringRes int msgId);

    void onShowSuccessMessage(String msg);

    void onShowSuccessMessage(@StringRes int msgId);

    void onShowReconnectMessage(String msg);

    void onShowErrorDialog(String msg);

}
