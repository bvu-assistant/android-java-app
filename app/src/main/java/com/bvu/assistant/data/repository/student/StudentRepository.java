package com.bvu.assistant.data.repository.student;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StudentRepository {
    private static final String TAG = "StudentRepository";



    public static LiveData<Student.Profile> getProfileInfo(String ssid) {
        final MutableLiveData<Student.Profile> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.LOGIN_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getProfile(ssid)
            .enqueue(new Callback<Student.Profile>() {
                @Override
                public void onResponse(@NotNull Call<Student.Profile> call, @NotNull Response<Student.Profile> response) {
                    Log.i(TAG, "onResponse: " + response.body());
                    result.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<Student.Profile> call, @NotNull Throwable t) {
                    Log.i(TAG, "onFailure: Failed to getProfile info");
                    result.setValue(null);
                }
            });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }

    public static LiveData<List<Student.AttendanceInfo>> getAttendanceInfo(String ssid) {
        final MutableLiveData<List<Student.AttendanceInfo>> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LOGIN_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getAttendanceInfo(ssid)
                .enqueue(new Callback<List<Student.AttendanceInfo>>() {
                    @Override
                    public void onResponse(Call<List<Student.AttendanceInfo>> call, Response<List<Student.AttendanceInfo>> response) {
                        Log.i(TAG, "onResponse: " + response.body());
                        result.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Student.AttendanceInfo>> call, Throwable t) {
                        Log.i(TAG, "onFailure: Failed to getAttendance info");
                        result.setValue(null);
                    }
                });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }

    public static LiveData<Student.LearningScores> getLearningScores(String ssid) {
        final MutableLiveData<Student.LearningScores> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LOGIN_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getLearningScores(ssid)
                .enqueue(new Callback<Student.LearningScores>() {
                    @Override
                    public void onResponse(Call<Student.LearningScores> call, Response<Student.LearningScores> response) {
                        Log.i(TAG, "onResponse: " + response.body());
                        result.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Student.LearningScores> call, Throwable t) {
                        Log.i(TAG, "onFailure: Failed to getLearningScores");
                        result.setValue(null);
                    }
                });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }


}
