package com.bvu.assistant.ui.main.home.functions.receipt;

import androidx.lifecycle.LiveData;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.data.repository.student.StudentRepository;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;


public class ReceiptFragmentViewModel extends BaseViewModel {
    private LiveData<List<Student.ReceiptInfo>> receiptsInfo;


    public LiveData<List<Student.ReceiptInfo>> getReceiptsInfo(String ssid) {
        receiptsInfo = StudentRepository.getReceiptsInfo(ssid);
        return receiptsInfo;
    }
}
