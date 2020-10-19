package com.bvu.assistant.viewmodel.retrofit.schedule;

import com.bvu.assistant.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleAPI {
    @GET("fetch/LichThi?")
    Call<Student.TestSchedule> get(@Query("ssid") String ssid);
}
