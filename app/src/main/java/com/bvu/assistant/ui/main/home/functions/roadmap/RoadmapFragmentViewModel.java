package com.bvu.assistant.ui.main.home.functions.roadmap;

import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.data.repository.student.StudentRepository;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;

public class RoadmapFragmentViewModel extends BaseViewModel {
    private LiveData<Student.RoadmapInfo> roadmapInfo;


    public LiveData<Student.RoadmapInfo> getRoadmapInfo(String ssid) {
        roadmapInfo = StudentRepository.getRoadmapInfo(ssid);
        return roadmapInfo;
    }
}
