package com.bvu.assistant.data.repository.retrofit.schedule;

import com.bvu.assistant.data.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TestScheduleAPI {
    @GET("fetch/LichThi?")
    Call<Student.TestSchedule> get(@Query("ssid") String ssid);
}
