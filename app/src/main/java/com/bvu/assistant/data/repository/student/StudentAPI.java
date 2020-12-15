package com.bvu.assistant.data.repository.student;

import com.bvu.assistant.data.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface StudentAPI {

    @GET("fetch/HoSo")
    Call<Student.Profile> getProfile(@Query("ssid") String ssid);



    @GET("fetch/DiemDanh")
    Call<List<Student.AttendanceInfo>> getAttendanceInfo(@Query("ssid") String ssid);

    @GET("fetch/DiemHocTap")
    Call<Student.LearningScores> getLearningScores(@Query("ssid") String ssid);

    @GET("fetch/DiemRL?")
    Call<List<Student.ExercisingScore>> getExercisingScores(@Query("ssid") String ssid);



    @GET("fetch/LichThi?")
    Call<Student.TestSchedule> getTestingSchedules(@Query("ssid") String ssid);

    @GET("fetch/LichHoc?")
    Call<Student.NormalSchedule> getNormalSchedules(@Query("ssid") String ssid);



    @GET("fetch/PhieuThu?")
    Call<List<Student.ReceiptInfo>> getReceiptsInfo(@Query("ssid") String ssid);

    @GET("fetch/CongNo?")
    Call<Student.LiabilityInfo> getLiabilityInfo(@Query("ssid") String ssid);

    @GET("fetch/CTKhung?")
    Call<Student.RoadmapInfo> getRoadMapInfo(@Query("ssid") String ssid);
}
