package com.example.android.architecture.blueprints.todoapp.data.source.local.helper;

import com.google.gson.Gson;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class PrefsHelper {

  private AppPreferences mPrefs;
  private Gson gson;

  @Inject
  public PrefsHelper(AppPreferences mPrefs, @Named("prefsGson") Gson gson) {
    this.mPrefs = mPrefs;
    this.gson = gson;
  }

  // Objects
  public <T> T getObject(String key, Class<T> aClass) {
    return getObject(key, aClass, "");
  }

  public <T> T getObject(String key, Class<T> aClass, String defaultValue) {
    String json = mPrefs.getString(key, defaultValue);
    T t = gson.fromJson(json, aClass);
    return t;
  }

  public void saveObject(String key, Object aObject) {
    String json = gson.toJson(aObject);
    mPrefs.put(key, json);
  }

  // Lists
  public <T> List<T> getList(String key, Class<T> aClass) {
    String json = mPrefs.getString(key, "");

    if (json.isEmpty())
      return new ArrayList<>(0);

    List<T> list = gson.fromJson(json, new ArrayList<T>().getClass());
    return list;
  }

  public void saveList(String key, List items) {
    String json = gson.toJson(items);
    mPrefs.put(key, json);
  }

  // Maps
  public <K, V> Map<K, V> getMap(String key, Class<K> mapKey, Class<V> mapValue) {
    String json = mPrefs.getString(key, "");

    if (json.isEmpty())
      return new HashMap<>(0);

    HashMap map = gson.fromJson(json, new HashMap<K, V>().getClass());
    return map;
  }

  public void saveMap(String key, Map map) {
    String json = gson.toJson(map);
    mPrefs.put(key, json);
  }
}
