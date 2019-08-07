package com.example.android.architecture.blueprints.todoapp.data.source.remote.helper;

import com.example.android.architecture.blueprints.todoapp.data.model.Token;
import com.example.android.architecture.blueprints.todoapp.data.source.local.helper.PrefsHelper;
import com.example.android.architecture.blueprints.todoapp.util.NetworkUtils;
import com.example.android.architecture.blueprints.todoapp.util.StringUtils;
import com.example.android.architecture.blueprints.todoapp.util.annotations.AuthScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.Authentication;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.Authenticator;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

@Singleton
public class ApiAuthenticator implements Authenticator {

  private PrefsHelper prefsHelper;
  private HttpLoggingInterceptor loggingInterceptor;

  @Inject
  public ApiAuthenticator(PrefsHelper prefsHelper, HttpLoggingInterceptor loggingInterceptor) {
    this.prefsHelper = prefsHelper;
    this.loggingInterceptor = loggingInterceptor;
  }

  @Nullable
  @Override
  public Request authenticate(@Nullable Route route, Response response) throws IOException {
    Request outgoingRequest = response.request();
    AuthScope authScope = NetworkUtils.getAnnotation(outgoingRequest, AuthScope.class);
    String accessToken = getAccessToken(authScope.scope());

    // retry the 'originalRequest' which encountered an authentication error
    // add new token into 'originalRequest' header and request again
    return outgoingRequest.newBuilder()
            .header("Authorization", accessToken)
            .build();
  }

  private String getAccessToken(Class<?> scope) {
    return Single.fromCallable(() -> getToken(scope))
                .doOnSuccess(token -> saveToken(token, scope))
                .map(token -> "${it.tokenType} ${it.accessToken}")
                .blockingGet();
  }

  private Token getToken(Class<?> authScope){
    String responseBody = null;
    Request authRequest = buildAuthRequest(authScope);
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

    try {
      responseBody = okHttpClient.newCall(authRequest).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return StringUtils.toObject(responseBody, Token.class);
  }

  private Request buildAuthRequest(Class<?> scope) {
    // TODO: get all checks for annotation from the retrofit itself
    // to ensure everything goes well
    Authentication authentication = (Authentication) scope.getAnnotations()[0];

    if (authentication == null)
      throw new IllegalArgumentException("@Authentication annotation not found. You should annotate the " +
              "api interface with @Authentication to add authentication for methods annotated" +
              " with @AuthScope annotation.");

    FormBody.Builder formBodyBuilder = new FormBody.Builder();
    for (String field : authentication.fields()) {
      String[] fieldInfo = field.split(": ");
      formBodyBuilder.add(fieldInfo[0], fieldInfo[1]);
    }

    Request request = new Request.Builder()
            .url(authentication.url())
            .post(formBodyBuilder.build())
            .build();
    return request;
  }

  private void saveToken(Token token, Class<?> scope) {
    prefsHelper.saveObject(scope.getSimpleName(), token);
  }
}
