package com.bvu.assistant.viewmodel.classes;


public class MainActivityViewModel {
    private String monthViewValue;


    public MainActivityViewModel(String monthViewValue) {
        this.monthViewValue = monthViewValue;
    }


    public String getMonthViewValue() {
        return monthViewValue;
    }

    public void setMonthViewValue(String monthViewValue) {
        this.monthViewValue = monthViewValue;
    }
}
