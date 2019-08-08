package com.example.android.architecture.blueprints.todoapp.util;

import java.lang.annotation.Annotation;

import okhttp3.Request;
import retrofit2.Invocation;

public class NetworkUtils {

  public static <T extends Annotation> T getAnnotation(Request request, Class<T> aClass) {
    Invocation invocation = request.tag(Invocation.class);
    return invocation.method().getAnnotation(aClass);
  }

  public static void validateFields(String... fields) {
    if (fields.length == 0) {
      throw new IllegalArgumentException("@Authentication fields is empty.");
    }
  }

  public static void validateField(String field) {
    int colon = field.indexOf(':');
    if (colon == -1 || colon == 0 || colon == field.length() - 1) {
      throw new IllegalArgumentException("@Authentication fields must be in the form \"Name: Value\". Found: \"" + field + "\"");
    }
  }

  public static void validateUrl(String url) {
    if (url.isEmpty()) {
      throw new IllegalArgumentException("@Authentication url is empty.");
    }
  }
}
