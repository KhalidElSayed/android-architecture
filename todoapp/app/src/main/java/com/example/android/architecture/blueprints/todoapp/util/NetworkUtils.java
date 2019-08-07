package com.example.android.architecture.blueprints.todoapp.util;

import java.lang.annotation.Annotation;

import okhttp3.Request;
import retrofit2.Invocation;

public class NetworkUtils {

  public static <T extends Annotation> T getAnnotation(Request request, Class<T> aClass) {
    Invocation invocation = request.tag(Invocation.class);
    return invocation.method().getAnnotation(aClass);
  }
}
