package com.example.android.architecture.blueprints.todoapp.data.source.remote.helper;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class ApiHelper {

  private OkHttpClient okHttpClient;
  private Gson gson;

  @Inject
  public ApiHelper(OkHttpClient okHttpClient, @Named("apiGson") Gson gson) {
    this.okHttpClient = okHttpClient;
    this.gson = gson;
  }

  public <T> T getAPI(String baseUrl, Class<T> apiClass) {
    return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(apiClass);
  }
}