package com.example.android.architecture.blueprints.todoapp.util.providers;

import android.content.Context;

import com.google.common.base.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Concrete implementation of the {@link BaseResourceProvider} interface.
 */
@Singleton
public class ResourceProvider implements BaseResourceProvider {

    @NonNull
    private final Context mContext;

    @Inject
    public ResourceProvider(@NonNull Context context) {
        mContext = Preconditions.checkNotNull(context, "context cannot be null");
    }

    @NonNull
    @Override
    public String getString(@StringRes final int id) {
        return mContext.getString(id);
    }

    @NonNull
    @Override
    public String getString(@StringRes final int id, final Object... formatArgs) {
        return mContext.getString(id, formatArgs);
    }
}
