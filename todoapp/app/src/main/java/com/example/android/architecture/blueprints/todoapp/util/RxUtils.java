package com.example.android.architecture.blueprints.todoapp.util;


import android.util.Pair;

import com.example.android.architecture.blueprints.todoapp.ui.base.view.BaseView;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

  public static <T> ObservableTransformer<T, T> applySchedulers() {
    return upstream -> upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T, V extends BaseView> ObservableTransformer<T, T> applyLoader(V view) {
    return upstream -> upstream.doOnSubscribe(disposable -> view.onShowLoader())
            .doOnError(throwable -> view.onHideLoader())
            .doOnComplete(view::onHideLoader);
  }

  public static <T> SingleTransformer<T, T> applySingleSchedulers() {
    return upstream -> upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T, V extends BaseView> SingleTransformer<T, T> applyLoaderOnSingle(V view) {
    return upstream -> upstream.doOnSubscribe(disposable -> view.onShowLoader())
            .doOnError(throwable -> view.onHideLoader())
            .doOnSuccess(value -> view.onHideLoader());
  }

  public static <T> ObservableTransformer<T, Pair<T, Integer>> mapWithIndex() {
    return upstream -> upstream.zipWith(Observable.range(0, Integer.MAX_VALUE), Pair::create);
  }

  public static void dispose(Disposable disposable) {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
}
