package com.bvu.assistant.data.repository.student;

import com.bvu.assistant.data.model.Student;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface StudentAPI {

    @GET("fetch/DiemDanh")
    Call<List<Student.AttendanceInfo>> getAttendanceInfo(@Query("ssid") String ssid);

    @GET("fetch/DiemHocTap")
    Call<Student.LearningScores> getLearningScores(@Query("ssid") String ssid);


    @GET("fetch/HoSo")
    Call<Student.Profile> getProfile(@Query("ssid") String ssid);

    @GET("fetch/LichThi?")
    Call<Student.TestSchedule> getTestingSchedules(@Query("ssid") String ssid);
}
