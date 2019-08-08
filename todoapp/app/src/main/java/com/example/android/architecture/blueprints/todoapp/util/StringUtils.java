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
}
