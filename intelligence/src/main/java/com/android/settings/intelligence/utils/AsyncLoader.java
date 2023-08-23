package com.android.settings.intelligence.utils;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

/**
 * This class fills in some boilerplate for AsyncTaskLoader to actually load things.
 *
 * Subclasses need to implement {@link AsyncLoader#loadInBackground()} to perform the actual
 * background task, and {@link AsyncLoader#onDiscardResult(T)} to clean up previously loaded
 * results.
 *
 * This loader is based on the MailAsyncTaskLoader from the AOSP EmailUnified repo.
 */
public abstract class AsyncLoader<T> extends AsyncTaskLoader<T> {
    private T mResult;

    public AsyncLoader(final Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        }

        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void deliverResult(final T data) {
        if (isReset()) {
            if (data != null) {
                onDiscardResult(data);
            }
            return;
        }

        final T oldResult = mResult;
        mResult = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldResult != null && oldResult != mResult) {
            onDiscardResult(oldResult);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (mResult != null) {
            onDiscardResult(mResult);
        }
        mResult = null;
    }

    @Override
    public void onCanceled(final T data) {
        super.onCanceled(data);

        if (data != null) {
            onDiscardResult(data);
        }
    }

    /**
     * Called when discarding the load results so subclasses can take care of clean-up or
     * recycling tasks. This is not called if the same result (by way of pointer equality) is
     * returned again by a subsequent call to loadInBackground, or if result is null.
     *
     * Note that this may be called concurrently with loadInBackground(), and in some circumstances
     * may be called more than once for a given object.
     *
     * @param result The value returned from {@link AsyncLoader#loadInBackground()} which
     *               is to be discarded.
     */
    protected abstract void onDiscardResult(final T result);
}