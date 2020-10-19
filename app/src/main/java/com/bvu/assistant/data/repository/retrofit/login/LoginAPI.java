package com.bvu.assistant.data.repository.retrofit.login;

import com.bvu.assistant.data.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPI {
    @POST("login/raw")
    Call<Login.Response> login(@Body Login.Request request);
}
