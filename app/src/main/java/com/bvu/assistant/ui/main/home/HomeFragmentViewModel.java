package com.bvu.assistant.ui.main.home;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.data.repository.student.StudentRepository;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;


public class HomeFragmentViewModel extends BaseViewModel {
    private static final String TAG = "HomeFragmentViewModel";

    private LiveData<Student.Profile> profileInfo;
    private LiveData<List<Student.AttendanceInfo>> attendanceInfo;
    private LiveData<Student.LearningScores> learningScores;



    public HomeFragmentViewModel() {
        Log.i(TAG, "HomeFragmentViewModel: HomeFragmentViewModel CTOR");

        /*profileInfo = StudentRepository.getProfileInfo("");
        learningScores = StudentRepository.getLearningScores("");
        attendanceInfo = StudentRepository.getAttendanceInfo("");*/
    }



    public LiveData<Student.Profile> getProfileInfo(String ssid) {
        profileInfo = StudentRepository.getProfileInfo(ssid);
        return profileInfo;
    }

    public LiveData<Student.LearningScores> getLearningScores(String ssid) {
        learningScores = StudentRepository.getLearningScores(ssid);
        return learningScores;
    }

    public LiveData<List<Student.AttendanceInfo>> getAttendanceInfo(String ssid) {
        attendanceInfo = StudentRepository.getAttendanceInfo(ssid);
        return attendanceInfo;
    }

}
