package com.bvu.assistant.viewmodel.retrofit.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPI {
    @POST("login/raw")
    Call<LoginResponse> login(@Body LoginRequest request);
}
