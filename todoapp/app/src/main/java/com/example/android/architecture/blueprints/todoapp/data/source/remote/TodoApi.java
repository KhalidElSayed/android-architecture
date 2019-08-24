package com.example.android.architecture.blueprints.todoapp.data.source.remote;

import com.example.android.architecture.blueprints.todoapp.data.model.Task;
import com.example.android.architecture.blueprints.todoapp.util.annotations.AuthScope;
import com.example.android.architecture.blueprints.todoapp.util.annotations.Authentication;
import com.example.android.architecture.blueprints.todoapp.util.annotations.NoAuth;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

@Authentication(url = "http://auth-todoapp.mocklab.io/connect/token",
                fields = {"grant_type: client_credentials",
                  "scope: UserManagementApi",
                  "client_id: mobile.to.usermanagement",
                  "client_secret: FrHT23xsWrTsDfbb574EZF7xWhQRcydAGF34C"})
public interface TodoApi {

  @AuthScope(scope = TodoApi.class)
  @POST(LOGIN_URL)
  Single<JsonObject> login(@Body Map<String, String> params);

  @NoAuth
  @GET(TASKS_URL)
  Single<List<Task>> getTasks();

  @NoAuth
  @GET(TASK_URL)
  Single<Task> getTask(@Path("id") int id);

  @NoAuth
  @POST(TASKS_URL)
  Completable addTask(@Body Task task);

  @NoAuth
  @PATCH(TASK_URL)
  Completable updateTask(@Path("id") int id, @Body Map<String, Integer> isCompleted);

  @NoAuth
  @DELETE(DELETE_COMPLETED_TASKS_URL)
  Completable deleteCompletedTasks();

  @NoAuth
  @DELETE(TASK_URL)
  Completable deleteTask(@Path("id") int id);

  @NoAuth
  @DELETE(TASKS_URL)
  Completable deleteTasks();

  // ---------------- Endpoints URLs -------------------//
  String LOGIN_URL = "login";
  String TASKS_URL = "tasks";
  String TASK_URL = "tasks/{id}";
  String DELETE_COMPLETED_TASKS_URL = "tasks?clearCompleted=true";
}