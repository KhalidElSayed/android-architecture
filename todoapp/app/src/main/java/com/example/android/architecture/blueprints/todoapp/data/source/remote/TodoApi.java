package com.example.android.architecture.blueprints.todoapp.data.source.remote;

import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.util.annotations.AuthScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.Authentication;
import com.example.android.architecture.blueprints.todoapp.util.annotations.NoAuth;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@Authentication(url = "https://auth.nagwa.com/connect/token",
                fields = {"grant_type: client_credentials",
                  "client_id: mobile.to.usermanagement",
                  "client_secret: Gynx2zMbrTsR27AfwyDMETFZF7xWhQRxGNn7yv5f",
                  "scope: UserManagementApi"})
public interface TodoApi {

  // ---------------- Endpoints URLs -------------------//
  String TASKS_URL = "tasks";

  @AuthScope(scope = TodoApi.class)
  @Headers("api-version: 2")
  @POST("PortalsLogin")
  Single<JsonObject> authenticateUser(@Body Map<String, String> params);

  @NoAuth
  @GET(TASKS_URL)
  Single<List<Task>> getTasks();
}