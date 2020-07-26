package com.bvu.assistant.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bvu.assistant.animation.MyTransformer;
import com.bvu.assistant.databinding.ActivityMainBinding;
import com.bvu.assistant.viewmodel.adapters.MainPagerAdapter;
import com.bvu.assistant.R;
import com.bvu.assistant.viewmodel.classes.MainActivityViewModel;
import com.bvu.assistant.viewmodel.helpers.TabLayoutHelper;
import com.bvu.assistant.viewmodel.interfaces.CommonNewsSearchCallback;
import com.bvu.assistant.viewmodel.interfaces.MainActivityBadger;
import com.bvu.assistant.viewmodel.interfaces.MainActivityChildFragmentGainer;
import com.bvu.assistant.viewmodel.interfaces.MainActivityMonthViewChanger;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.InstanceIdResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements MainActivityBadger, MainActivityMonthViewChanger, MainActivityChildFragmentGainer {
    private final String TAG = "MainActivity";
    private ActivityMainBinding B;

    private MainPagerAdapter pagerAdapter;
    private ArrayList<String> mainScreenTabBarTitles;
    private ArrayList<Integer> mainScreenTabBarIcons;
    private ArrayList<Fragment> childFragments;
    private List<Integer> mainScreenBadgeHolder;

    private int headlinesNewsBadgeCounter = 0;
    private int studentNewsBadgeCounter = 0;
    private int currentTabIndex = 0;
    private String currentMonthValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = DataBindingUtil.setContentView(this, R.layout.activity_main);
        B.setViewmodel(new MainActivityViewModel(""));
        B.executePendingBindings();

        initAndMapping();
        handleTabBar();
        handleFirebaseInstanceId();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, intent.getData().toString(), Toast.LENGTH_SHORT).show();
    }



    private void initAndMapping() {
        childFragments = new ArrayList<>();
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), 0, this);

        //  TabLayout's titles
        mainScreenTabBarTitles = new ArrayList<String>();
        mainScreenTabBarTitles.add(getResources().getString(R.string.main_tabbar_first_title));
        mainScreenTabBarTitles.add(getResources().getString(R.string.main_tabbar_second_title));
        mainScreenTabBarTitles.add(getResources().getString(R.string.main_tabbar_third_title));
        mainScreenTabBarTitles.add(getResources().getString(R.string.main_tabbar_fourth_title));
        mainScreenTabBarTitles.add(getResources().getString(R.string.main_tabbar_fifth_title));


        //  TabLayout's icons
        mainScreenTabBarIcons = new ArrayList<Integer>();
        mainScreenTabBarIcons.add(R.drawable.icon_note);
        mainScreenTabBarIcons.add(R.drawable.icon_calendar);
        mainScreenTabBarIcons.add(R.drawable.icon_home);
        mainScreenTabBarIcons.add(R.drawable.icon_bell);
        mainScreenTabBarIcons.add(R.drawable.icon_hamburger_menu);

        //  Create an immutable list for the badgeHolder
        mainScreenBadgeHolder = Arrays.asList(0, 0, 0);


//        B.edtSearchNews.setOnEditorActionListener((v, actionId, event) -> {
//
//            for (Fragment frm: childFragments) {
//                ((CommonNewsSearchCallback)frm).onTypeComplete(v.getText().toString());
//            }
//
//            return false;
//        });

        B.edtSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (Fragment frm: childFragments) {
                    ((CommonNewsSearchCallback)frm).onTypeComplete(s.toString());
                }
            }
        });
    }

    private void handleFirebaseInstanceId() {
        OnCompleteListener<InstanceIdResult> onCompleteListener;

        onCompleteListener = task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            String token = Objects.requireNonNull(task.getResult()).getToken();

            // Log and toast
            String msg = String.format("Instance ID: %s", token);
            Log.w(TAG, msg);
//            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        };


        Task<InstanceIdResult> instanceId = FirebaseInstanceId.getInstance().getInstanceId();
        instanceId.addOnCompleteListener(onCompleteListener);
    }



    private void changeActionBarTitle(int tabIndex) {
        B.actionBarTitle.setText(mainScreenTabBarTitles.get(tabIndex));
    }

    private void updateMonthValue() {
        B.actionBarMonthView.setText(currentTabIndex == 1? currentMonthValue: "");
    }


    private void handleTabBar() {
        B.mainViewPager.setAdapter(pagerAdapter);
        B.mainViewPager.setOffscreenPageLimit(4);

        B.mainBottomNavBar.addOnTabSelectedListener(onTabSelectedListener);
        B.mainBottomNavBar.setupWithViewPager(B.mainViewPager);
        B.mainBottomNavBar.setElevation(5f);
        B.mainBottomNavBar.setRotationX(180);
        setTabLayoutIcons();

        TabLayoutHelper.setViewPagerTransforming(B.mainViewPager, MyTransformer.Transformer.Fading);
        TabLayoutHelper.setDefaultSelectedTabItem(B.mainBottomNavBar, 1);
        TabLayoutHelper.reFlipTabBarItems(B.mainBottomNavBar);
    }

    private void setTabLayoutIcons() {
        for (int i = 0; i < B.mainBottomNavBar.getTabCount(); ++i) {
            B.mainBottomNavBar.getTabAt(i).setIcon(mainScreenTabBarIcons.get(i));
            TooltipCompat.setTooltipText(B.mainBottomNavBar.getTabAt(i).view, mainScreenTabBarTitles.get(i));
        }
    }


    private TabLayout.OnTabSelectedListener onTabSelectedListener;
    {
        onTabSelectedListener = new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                changeActionBarTitle(tab.getPosition());
                MainActivity.this.currentTabIndex = tab.getPosition();
                updateMonthValue();

                if (tab.getPosition() != 0) {
                    B.edtSearchNews.setVisibility(View.GONE);
                }
                else {
                    B.edtSearchNews.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }



    private void updateNewsBadgeCount() {
        TabLayoutHelper.setBadgeNumber(B.mainBottomNavBar, 0, headlinesNewsBadgeCounter + studentNewsBadgeCounter);
    }


    @Override
    public void updateHeadlinesNewsCount(int length) {
        headlinesNewsBadgeCounter = length;
        updateNewsBadgeCount();
    }

    @Override
    public void updateStudentNewsCount(int length) {
        studentNewsBadgeCounter = length;
        updateNewsBadgeCount();
    }

    @Override
    public void onMonthValueChange(String value) {
        currentMonthValue = value;
        updateMonthValue();
    }

    @Override
    public void onNewFragmentAttached(Fragment fragment) {
        Log.i("NewsCommonFragment", "onNewFragmentAttached: " + fragment.getClass());
        childFragments.add(fragment);
    }
}