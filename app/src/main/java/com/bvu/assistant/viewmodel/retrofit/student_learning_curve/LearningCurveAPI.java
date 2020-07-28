package com.bvu.assistant.viewmodel.retrofit.student_learning_curve;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LearningCurveAPI {
    @GET("fetch/DiemHocTap")
    Call<StudentLearningCurve> get(@Query("ssid") String ssid);
}
