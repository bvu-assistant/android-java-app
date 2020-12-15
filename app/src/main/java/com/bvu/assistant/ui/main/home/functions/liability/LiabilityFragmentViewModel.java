package com.bvu.assistant.ui.main.home.functions.liability;

import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.data.repository.student.StudentRepository;
import com.bvu.assistant.ui.base.BaseViewModel;

public class LiabilityFragmentViewModel extends BaseViewModel {
    private LiveData<Student.LiabilityInfo> liabilityInfo;

    public LiveData<Student.LiabilityInfo> getLiabilityInfo(String ssid) {
        liabilityInfo = StudentRepository.getLiabilityInfo(ssid);
        return liabilityInfo;
    }
}
