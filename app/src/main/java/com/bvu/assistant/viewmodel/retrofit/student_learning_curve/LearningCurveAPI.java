package com.bvu.assistant.viewmodel.retrofit.student_learning_curve;

import com.bvu.assistant.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LearningCurveAPI {
    @GET("fetch/DiemHocTap")
    Call<Student.LearningScores> get(@Query("ssid") String ssid);
}
