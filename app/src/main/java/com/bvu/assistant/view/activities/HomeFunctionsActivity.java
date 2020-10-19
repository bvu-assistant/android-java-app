package com.bvu.assistant.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.ActivityHomeFunctionsBinding;
import com.bvu.assistant.view.fragments.home.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFunctionsActivity extends AppCompatActivity {
    private ActivityHomeFunctionsBinding B;
    private ArrayList<String> functionsList;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_home_functions);
        fragmentManager = getSupportFragmentManager();

        //  khởi tạo danh sách chức năng
        initFunctionsList();

        Intent receivedIntent = getIntent();
        String functions = receivedIntent.getStringExtra("functions");

        B.actionBar.setTitle(functions);
        B.actionBar.setNavigationOnClickListener( v -> {
            finish();
        });


        handleFunctions(functions);
    }

    private void handleFunctions(String functions) {
        if (functions.equals(functionsList.get(0))) {
            fragmentManager
                .beginTransaction()
                .replace(B.fragmentContainer.getId(), new ProfileFragment())
                .commit();
        }
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