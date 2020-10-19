package com.bvu.assistant.data.repository.student;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentRepository {

    public static LiveData<Student.Profile> getProfileInfo(Context context, String ssid) {
        final MutableLiveData<Student.Profile> result = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(context.getResources().getString(R.string.login_api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        StudentAPI api = retrofit.create(StudentAPI.class);
        api.getProfile(ssid)
            .enqueue(new Callback<Student.Profile>() {
                @Override
                public void onResponse(@NotNull Call<Student.Profile> call, @NotNull Response<Student.Profile> response) {
                    result.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<Student.Profile> call, @NotNull Throwable t) {
                    result.setValue(null);
                }
            });

        //  dữ liệu được đẩy ra khi setValue()
        return result;
    }



}
