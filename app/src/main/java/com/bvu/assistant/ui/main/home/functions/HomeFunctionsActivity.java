package com.bvu.assistant.ui.main.home.functions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.ActivityHomeFunctionsBinding;
import com.bvu.assistant.databinding.BaseActivityLayoutBinding;
import com.bvu.assistant.ui.base.BaseActivity;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;
import com.bvu.assistant.ui.main.home.functions.attendance.AttendanceFragment;
import com.bvu.assistant.ui.main.home.functions.award.AwardFragment;
import com.bvu.assistant.ui.main.home.functions.exercising_scores.ExercisingScoreFragment;
import com.bvu.assistant.ui.main.home.functions.learning_scores.LearningScoresFragment;
import com.bvu.assistant.ui.main.home.functions.liability.LiabilityFragment;
import com.bvu.assistant.ui.main.home.functions.office365.Office365Fragment;
import com.bvu.assistant.ui.main.home.functions.profile.ProfileFragment;
import com.bvu.assistant.ui.main.home.functions.receipt.ReceiptFragment;
import com.bvu.assistant.ui.main.home.functions.roadmap.RoadmapFragment;
import com.bvu.assistant.utils.StatusBarUtils;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFunctionsActivity extends BaseActivity<ActivityHomeFunctionsBinding, BaseViewModel> {
    public static final String INTENT_KEY = "functions";
    private ArrayList<String> functionsList;
    private FragmentManager fragmentManager;
    private String ssid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_functions;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }

    @Override
    public Intent newIntent(Context context) {
        return new Intent(context, HomeFunctionsActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        //  khởi tạo danh sách chức năng
        reSetupStatusBarHeight();
        initFunctionsList();

        Intent receivedIntent = getIntent();
        ssid = receivedIntent.getStringExtra("ssid");
        String functions = receivedIntent.getStringExtra(INTENT_KEY);

        B.actionBar.setTitle(functions);
        B.actionBar.setNavigationOnClickListener( v -> {
            finish();
        });


        if (functions != null) {
            if (functions.equals(functionsList.get(0))) {
                Student.Profile profileInfo = new Gson().fromJson(receivedIntent.getStringExtra("profile"), Student.Profile.class);
                replaceFragment(new ProfileFragment(profileInfo));
                return;
            }

            if (functions.equals(functionsList.get(1))) {
                replaceFragment(new RoadmapFragment(ssid));
                return;
            }

            if (functions.equals(functionsList.get(2))) {
                Student.Profile.PersonalProfile.BVU365 office365Info = new Gson().fromJson(receivedIntent.getStringExtra("office365"), Student.Profile.PersonalProfile.BVU365.class);
                replaceFragment(new Office365Fragment(office365Info));
                return;
            }

            if (functions.equals(functionsList.get(3))) {
                replaceFragment(new LiabilityFragment(ssid));
                return;
            }

            if (functions.equals(functionsList.get(4))) {
                replaceFragment(new ReceiptFragment(ssid));
                return;
            }

            if (functions.equals(functionsList.get(5))) {
                replaceFragment(new AwardFragment(ssid));
                return;
            }

            if (functions.equals(functionsList.get(6))) {
                Student.LearningScores learningScores = new Gson().fromJson(receivedIntent.getStringExtra("learningScores"), Student.LearningScores.class);
                replaceFragment(new LearningScoresFragment(learningScores));
                return;
            }

            if (functions.equals(functionsList.get(7))) {
                replaceFragment(new ExercisingScoreFragment(ssid));
                return;
            }

            if (functions.equals(functionsList.get(8))) {
                Student.AttendanceInfo[] attendanceInfo = new Gson().fromJson(receivedIntent.getStringExtra("attendance"), Student.AttendanceInfo[].class);
                replaceFragment(new AttendanceFragment(Arrays.asList(attendanceInfo)));
                return;
            }
        }
    }


    private void reSetupStatusBarHeight() {
        int sHeight = StatusBarUtils.getHeight(this);

        if (sHeight > 0) {
            B.actionBar.setPadding(0, sHeight, 0, 0);
        }
    }

    private void replaceFragment(BaseFragment fragment) {
        fragmentManager
            .beginTransaction()
            .replace(B.fragmentContainer.getId(), fragment)
            .commit();
    }

    private void initFunctionsList() {
        this.functionsList = new ArrayList<>();

        functionsList.add(getResources().getString(R.string.homeFrm_grid_firstTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_secondTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_thirdTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_fourthTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_fifthTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_sixthTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_seventhTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_eighthTitle));
        functionsList.add(getResources().getString(R.string.homeFrm_grid_ninthTitle));
    }

}