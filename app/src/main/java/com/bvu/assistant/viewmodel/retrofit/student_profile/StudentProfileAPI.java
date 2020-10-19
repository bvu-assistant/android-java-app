package com.bvu.assistant.viewmodel.retrofit.student_profile;

import com.bvu.assistant.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StudentProfileAPI {
    @GET("fetch/HoSo")
    Call<Student.Profile> getProfile(@Query("ssid") String ssid);
}
