package com.bvu.assistant.ui.main.home.functions.award;

import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.data.repository.student.StudentRepository;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;

public class AwardFragmentViewModel extends BaseViewModel {
    private LiveData<List<Student.ExercisingScore>> exercisingScores;

    public LiveData<List<Student.ExercisingScore>> getExercisingScores(String ssid) {
        exercisingScores = StudentRepository.getExercisingScores(ssid);
        return exercisingScores;
    }
}
