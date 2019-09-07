package com.example.android.architecture.blueprints.todoapp.ui.base.view;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.architecture.blueprints.todoapp.util.DialogUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * shown of progress dialogs and toast and implements the baseView.
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

    private KProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
    }

    @Override
    public void onShowLoader() {
        mProgressHUD = KProgressHUD.create(this)
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
        onShowToast(getString(msgId));
    }

    @Override
    public void onShowToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowErrorMessage(String msg) {
        DialogUtils.showMessage(this, msg, DialogUtils.MessageType.ERROR);
    }

    @Override
    public void onShowErrorMessage(@StringRes int msgId) {
        onShowErrorMessage(getString(msgId));
    }

    @Override
    public void onShowSuccessMessage(String msg) {
        DialogUtils.showMessage(this, msg, DialogUtils.MessageType.SUCCESS);
    }

    @Override
    public void onShowSuccessMessage(@StringRes int msgId) {
        onShowSuccessMessage(getString(msgId));
    }

    @Override
    public void onShowReconnectMessage(String msg) {

    }

    @Override
    public void onShowErrorDialog(String msg) {
    }
}
