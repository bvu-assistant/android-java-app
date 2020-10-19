package com.bvu.assistant.viewmodel.retrofit.student_attendance;

import com.bvu.assistant.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AttendanceAPI {
    @GET("fetch/DiemDanh")
    Call<List<Student.AttendanceInfo>> getAttendance(@Query("ssid") String ssid);
}
