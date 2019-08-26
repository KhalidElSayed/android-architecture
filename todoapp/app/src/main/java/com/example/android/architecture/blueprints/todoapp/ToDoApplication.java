package com.example.android.architecture.blueprints.todoapp;

import android.os.StrictMode;

import com.example.android.architecture.blueprints.todoapp.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

/**
 * Application class, used for setting the StrictMode.
 */
public class ToDoApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            setStrictMode();
            Timber.plant(new Timber.DebugTree());
        }
        super.onCreate();

        RxJavaPlugins.setErrorHandler(err -> Timber.e("RxPluginError called with: err = [" + err + "]"));
    }

    @Override
    protected AndroidInjector<ToDoApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
