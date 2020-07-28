package com.bvu.assistant.viewmodel.retrofit.student_profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StudentProfileAPI {
    @GET("fetch/HoSo")
    Call<StudentProfile> getProfile(@Query("ssid") String ssid);
}
