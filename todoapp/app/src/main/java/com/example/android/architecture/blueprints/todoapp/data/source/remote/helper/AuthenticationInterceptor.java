package com.example.android.architecture.blueprints.todoapp.data.source.remote.helper;

import com.example.android.architecture.blueprints.todoapp.data.model.Token;
import com.example.android.architecture.blueprints.todoapp.data.source.local.helper.PrefsHelper;
import com.example.android.architecture.blueprints.todoapp.util.NetworkUtils;
import com.example.android.architecture.blueprints.todoapp.util.annotations.AuthScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.NoAuth;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class AuthenticationInterceptor implements Interceptor {

  private PrefsHelper prefsHelper;

  @Inject
  public AuthenticationInterceptor(PrefsHelper prefsHelper) {
    this.prefsHelper = prefsHelper;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = null;
    Request originalRequest = chain.request();

    /*
     * NOTE: we'll change the mechanism by build and call the auth api directly inside the Authenticator class
     * using Okhttp lib.
     * */
    NoAuth noAuth = NetworkUtils.getAnnotation(originalRequest, NoAuth.class);

    if (noAuth != null) {
      request = originalRequest;
    } else {
      String accessToken = getCashedToken(originalRequest);
      request = originalRequest.newBuilder()
              .header("Authorization", accessToken)
              .method(originalRequest.method(), originalRequest.body())
              .build();
    }

    return chain.proceed(request);
  }

  private String getCashedToken(Request originalRequest) {
    String accessToken = "";
    AuthScope authScope = NetworkUtils.getAnnotation(originalRequest, AuthScope.class);

    // get the accessToken
    Token token = prefsHelper.getObject(authScope.scope().getSimpleName(), Token.class);
    if (token != null) {
      accessToken = token.getTokenType() + " " + token.getAccessToken();
    }

    return accessToken;
  }
}
