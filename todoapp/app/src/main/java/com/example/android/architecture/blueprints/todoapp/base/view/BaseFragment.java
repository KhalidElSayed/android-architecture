package com.example.android.architecture.blueprints.todoapp.base.view;

import android.widget.Toast;

import com.example.android.architecture.blueprints.todoapp.util.DialogUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import androidx.annotation.StringRes;
import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment implements BaseView {

    private KProgressHUD mProgressHUD;

    @Override
    public void onShowLoader() {
        mProgressHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//              .setLabel("Please wait")
//              .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        mProgressHUD.show();
    }

    @Override
    public void onHideLoader() {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
        }
    }

    @Override
    public boolean isLoaderShown() {
        return mProgressHUD != null && mProgressHUD.isShowing();
    }

    @Override
    public void onShowToast(@StringRes int msgId) {
        onShowToast(getActivity().getString(msgId));
    }

    @Override
    public void onShowToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowErrorMessage(String msg) {
        DialogUtils.showMessage(getActivity(), msg, DialogUtils.MessageType.ERROR);
    }

    @Override
    public void onShowErrorMessage(@StringRes int msgId) {
        // add getActivity() before getString() to resolve error on Android 4.4
        onShowErrorMessage(getActivity().getString(msgId));
    }

    @Override
    public void onShowSuccessMessage(String msg) {
        DialogUtils.showMessage(getActivity(), msg, DialogUtils.MessageType.SUCCESS);
    }

    @Override
    public void onShowSuccessMessage(@StringRes int msgId) {
        onShowSuccessMessage(getActivity().getString(msgId));
    }

    @Override
    public void onShowReconnectMessage(String msg) {

    }

    @Override
    public void onShowErrorDialog(String msg) {

    }
}
