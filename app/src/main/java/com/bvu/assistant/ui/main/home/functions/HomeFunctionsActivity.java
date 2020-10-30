package com.bvu.assistant.ui.main.home.functions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.ActivityHomeFunctionsBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.home.functions.profile.ProfileFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFunctionsActivity extends AppCompatActivity {
    public static final String INTENT_KEY = "functions";

    private ActivityHomeFunctionsBinding B;
    private ArrayList<String> functionsList;
    private FragmentManager fragmentManager;
    private Student.Profile profileInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_home_functions);
        fragmentManager = getSupportFragmentManager();

        //  khởi tạo danh sách chức năng
        initFunctionsList();

        Intent receivedIntent = getIntent();
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
        }
    }

    private void handleFunctions(String functions) {
        /*if (functions.equals(functionsList.get(1))) {
            replaceFragment(new ProfileFragment());
            return;
        }

        if (functions.equals(functionsList.get(2))) {
            replaceFragment(new ProfileFragment());
            return;
        }*/
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