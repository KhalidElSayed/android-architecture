package com.example.android.architecture.blueprints.todoapp.util;

import com.google.gson.Gson;

public class StringUtils {

  public static <T> T toObject(String s, Class<T> aClass) {
    return new Gson().fromJson(s, aClass);
  }

  public static boolean isValidString(CharSequence charSequence) {
    return charSequence != null && !charSequence.toString().isEmpty();
  }

  public static boolean isValidInteger(String value) {
    return isValidString(value) && Integer.parseInt(value) != 0;
  }

  public static boolean isEnglish(String text) {
    return text.matches("[a-zA-Z]+");
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
