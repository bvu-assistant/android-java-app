package com.bvu.assistant.ui.main.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;


public class HomeFragmentViewModel extends BaseViewModel {
    private LiveData<Student.Profile> observableProfile;


}
