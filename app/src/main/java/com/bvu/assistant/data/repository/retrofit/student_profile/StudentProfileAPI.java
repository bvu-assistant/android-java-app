package com.bvu.assistant.data.repository.retrofit.student_profile;

import com.bvu.assistant.data.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StudentProfileAPI {
    @GET("fetch/HoSo")
    Call<Student.Profile> getProfile(@Query("ssid") String ssid);
}
