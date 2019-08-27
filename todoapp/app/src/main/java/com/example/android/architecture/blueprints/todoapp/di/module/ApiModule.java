package com.example.android.architecture.blueprints.todoapp.di.module;

import com.example.android.architecture.blueprints.todoapp.BuildConfig;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TodoApi;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.helper.ApiAuthenticator;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.helper.ApiHelper;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.helper.AuthenticationInterceptor;
import com.example.android.architecture.blueprints.todoapp.util.annotations.api.BaseUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ApiModule {

  @Provides
  @Singleton
  @BaseUrl
  String provideBaseUrl() {
    return BuildConfig.BASE_URL;
  }

  @Provides
  @Singleton
  @Named("apiGson")
  Gson provideGson() {
    return new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor provideOkHttpLogging() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return loggingInterceptor;
  }

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient(AuthenticationInterceptor authInterceptor,
                                   HttpLoggingInterceptor loggingInterceptor,
                                   ApiAuthenticator apiAuthenticator) {
    return new OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(apiAuthenticator)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .build();
  }

  @Provides
  @Singleton
  @Named("todoApi")
  TodoApi provideTodoApi(@BaseUrl String baseUrl, ApiHelper apiHelper) {
    return apiHelper.getAPI(baseUrl, TodoApi.class);
  }
}
