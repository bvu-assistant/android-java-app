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
            .baseUrl(Constants.SERVER_HOST_PATH)
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



    /* schedules */
    public static LiveData<Student.NormalSchedule> getNormalSchedules(String ssid) {
        final MutableLiveData<Student.NormalSchedule> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getNormalSchedules(ssid)
            .enqueue(new Callback<Student.NormalSchedule>() {
                @Override
                public void onResponse(Call<Student.NormalSchedule> call, Response<Student.NormalSchedule> response) {
                    /*Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));*/
                    result.setValue(response.body());
                }

                @Override
                public void onFailure(Call<Student.NormalSchedule> call, Throwable t) {
                    Log.i(TAG, "onFailure: Failed to getNormalSchedules info", t.getCause());
                    result.setValue(null);
                }
            });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }

    public static LiveData<Student.TestSchedule> getTestSchedules(String ssid) {
        final MutableLiveData<Student.TestSchedule> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getTestingSchedules(ssid)
            .enqueue(new Callback<Student.TestSchedule>() {
                @Override
                public void onResponse(Call<Student.TestSchedule> call, Response<Student.TestSchedule> response) {
                    Log.i(TAG, "onResponse: " + response.body());
                    result.setValue(response.body());
                }

                @Override
                public void onFailure(Call<Student.TestSchedule> call, Throwable t) {
                    Log.i(TAG, "onFailure: Failed to getNormalSchedules info");
                    result.setValue(null);
                }
            });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }



    /* scores */
    public static LiveData<List<Student.AttendanceInfo>> getAttendanceInfo(String ssid) {
        final MutableLiveData<List<Student.AttendanceInfo>> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
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

    public static LiveData<List<Student.ExercisingScore>> getExercisingScores(String ssid) {
        final MutableLiveData<List<Student.ExercisingScore>> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getExercisingScores(ssid)
                .enqueue(new Callback<List<Student.ExercisingScore>>() {
                    @Override
                    public void onResponse(Call<List<Student.ExercisingScore>> call, Response<List<Student.ExercisingScore>> response) {
                        Log.i(TAG, "onResponse: " + response.body());
                        result.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Student.ExercisingScore>> call, Throwable t) {
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
                .baseUrl(Constants.SERVER_HOST_PATH)
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

    public static LiveData<Student.RoadmapInfo> getRoadmapInfo(String ssid) {
        final MutableLiveData<Student.RoadmapInfo> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getRoadMapInfo(ssid)
                .enqueue(new Callback<Student.RoadmapInfo>() {
                    @Override
                    public void onResponse(Call<Student.RoadmapInfo> call, Response<Student.RoadmapInfo> response) {
                        Log.i(TAG, "onResponse: " + response.body());
                        result.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Student.RoadmapInfo> call, Throwable t) {
                        Log.i(TAG, "onFailure: Failed to getLearningScores", t.getCause());
                        result.setValue(null);
                    }
                });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }

    public static LiveData<Student.LiabilityInfo> getLiabilityInfo(String ssid) {
        final MutableLiveData<Student.LiabilityInfo> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getLiabilityInfo(ssid)
                .enqueue(new Callback<Student.LiabilityInfo>() {
                    @Override
                    public void onResponse(Call<Student.LiabilityInfo> call, Response<Student.LiabilityInfo> response) {
                        Log.i(TAG, "onResponse: " + response.body());
                        result.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Student.LiabilityInfo> call, Throwable t) {
                        Log.i(TAG, "onFailure: Failed to getLearningScores", t.getCause());
                        result.setValue(null);
                    }
                });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }



    /* finance info */
    public static LiveData<List<Student.ReceiptInfo>> getReceiptsInfo(String ssid) {
        final MutableLiveData<List<Student.ReceiptInfo>> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getReceiptsInfo(ssid)
                .enqueue(new Callback<List<Student.ReceiptInfo>>() {
                    @Override
                    public void onResponse(Call<List<Student.ReceiptInfo>> call, Response<List<Student.ReceiptInfo>> response) {
                        Log.i(TAG, "onResponse: " + response.body().get(0).getTotalCost() + " - " + response.isSuccessful());

                        try {
                            result.setValue(response.body());
                        }
                        catch (Exception e) {
                            Log.d(TAG, "onResponse: ", e.getCause());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Student.ReceiptInfo>> call, Throwable t) {
                        Log.i(TAG, "onFailure: Failed to getReceiptsInfo", t.getCause());
                        result.setValue(null);
                    }
                });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }

}
