package com.bvu.assistant.ui.main.calendar;

import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.data.repository.student.StudentRepository;
import com.bvu.assistant.ui.base.BaseViewModel;

public class CalendarFragmentViewModel extends BaseViewModel {
    private LiveData<Student.NormalSchedule> normalSchedules;
    private LiveData<Student.TestSchedule> testSchedules;

    public CalendarFragmentViewModel() {

    }

    public LiveData<Student.NormalSchedule> getNormalSchedules(String ssid) {
        normalSchedules = StudentRepository.getNormalSchedules(ssid);
        return normalSchedules;
    }

    public LiveData<Student.TestSchedule> getTestSchedules(String ssid) {
        testSchedules = StudentRepository.getTestSchedules(ssid);
        return testSchedules;
    }
}
