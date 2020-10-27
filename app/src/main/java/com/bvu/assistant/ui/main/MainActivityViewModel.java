package com.bvu.assistant.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> isActionBarShowing = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSearchBarShowing = new MutableLiveData<>();
    public MutableLiveData<String> actionBarTitle = new MutableLiveData<>();
    public MutableLiveData<MonthTitle> monthValue = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Integer>> bottomNavBadges = new MutableLiveData<>();


    public MainActivityViewModel() {
        isActionBarShowing.setValue(false);
        isSearchBarShowing.setValue(false);
        monthValue.setValue(new MonthTitle(true, "Tháng 7"));

        bottomNavBadges.setValue(new ArrayList<>());
        bottomNavBadges.getValue().add(0);
        bottomNavBadges.getValue().add(0);
        bottomNavBadges.getValue().add(0);
        bottomNavBadges.getValue().add(0);
        bottomNavBadges.getValue().add(0);
    }


    public static class MonthTitle {
        private boolean visible;
        private String content;

        public MonthTitle(boolean visible, String content) {
            this.visible = visible;
            this.content = content;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
