package com.bvu.assistant.ui.login;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.bvu.assistant.ui.base.BaseViewModel;


public class LoginActivityViewModel extends BaseViewModel {

    public MutableLiveData<String> StudentId = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> Remembered = new MutableLiveData<>();
    public MutableLiveData<LoginCredentials> Credentials = new MutableLiveData<>();


    public void onBtnLoginClicked(View v) {
        Credentials.setValue(new LoginCredentials(StudentId.getValue(), Password.getValue()));
    }
}

class LoginCredentials {
    private String studentId;
    private String password;

    public LoginCredentials(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
