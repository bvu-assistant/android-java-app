package com.bvu.assistant.view.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.ActivityMainBinding;
import com.bvu.assistant.view.fragments.ConnectingFragment;
import com.bvu.assistant.view.fragments.HomeFragment;
import com.bvu.assistant.view.fragments.NewsFragment;
import com.bvu.assistant.view.fragments.SettingsFragment;
import com.bvu.assistant.view.fragments.customcalendar.CalendarFragment;
import com.bvu.assistant.viewmodel.interfaces.CommonNewsSearchCallback;
import com.bvu.assistant.viewmodel.interfaces.MainActivityChildFragmentGainer;
import com.bvu.assistant.viewmodel.interfaces.MainActivityMonthViewChanger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Objects;

import qiu.niorgai.StatusBarCompat;


public class MainActivity extends AppCompatActivity implements MainActivityMonthViewChanger, MainActivityChildFragmentGainer {
    private static final String SAVED_STATE_CONTAINER_KEY = "ContainerKey";
    private static final String SAVED_STATE_CURRENT_KEY = "CurrentKey";

    private final String TAG = "MainActivity";
    public ActivityMainBinding B;

    private ArrayList<Fragment> childBottomNavFragments; //  lưu giữ trạng thái của các fragment con
    private ArrayList<String> mainScreenActionBarBarTitles;     //  listing các tiêu đề của ActionBar
    private ArrayList<Fragment> childNewsCommonFragments;     //  lưu giữ các fragment con (trong NewsCommon) | dùng cho searching
    public Fragment activeFragment;

    private int currentTabIndex = 0;
    private String currentMonthValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = DataBindingUtil.setContentView(this, R.layout.activity_main);
        B.executePendingBindings();

        initAndMapping();
        handleBottomNavBar();
        handleFirebaseInstanceId();
    }

    private void initAndMapping() {
        childNewsCommonFragments = new ArrayList<>();


        // list tiêu đề của ActionBar
        mainScreenActionBarBarTitles = new ArrayList<String>();
        mainScreenActionBarBarTitles.add(getResources().getString(R.string.main_tabbar_first_title));
        mainScreenActionBarBarTitles.add(getResources().getString(R.string.main_tabbar_second_title));
        mainScreenActionBarBarTitles.add(getResources().getString(R.string.main_tabbar_third_title));
        mainScreenActionBarBarTitles.add(getResources().getString(R.string.main_tabbar_fourth_title));
        mainScreenActionBarBarTitles.add(getResources().getString(R.string.main_tabbar_fifth_title));


        //  list các fragments của BottomNav
        childBottomNavFragments = new ArrayList<>();
        childBottomNavFragments.add(new ConnectingFragment());
        childBottomNavFragments.add(new CalendarFragment());
        childBottomNavFragments.add(new HomeFragment());
        childBottomNavFragments.add(new NewsFragment());
        childBottomNavFragments.add(new SettingsFragment());


        //  xử lý sự kiện edtSearch text changed
        B.edtSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (Fragment frm: childNewsCommonFragments) {
                    ((CommonNewsSearchCallback)frm).onTypeComplete(s.toString());
                }
            }
        });
    }



    private void handleBottomNavBar() {
        //  fragment hiển thị cho người dùng
        activeFragment = childBottomNavFragments.get(2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        //  khởi tạo va thêm tất cả các fragment (của BottomNav) trong Activity này
        fragmentTransaction
            .add(R.id.mainFragmentContainer, childBottomNavFragments.get(0), "ConnectingFragment")
            .hide(childBottomNavFragments.get(0));
        fragmentTransaction
            .add(R.id.mainFragmentContainer, childBottomNavFragments.get(1), "CalendarFragment")
            .hide(childBottomNavFragments.get(1));
        fragmentTransaction
                .add(R.id.mainFragmentContainer, activeFragment, "HomeFragment");
        fragmentTransaction
            .add(R.id.mainFragmentContainer, childBottomNavFragments.get(3), "NewsFragment")
            .hide(childBottomNavFragments.get(3));
        fragmentTransaction
            .add(R.id.mainFragmentContainer, childBottomNavFragments.get(4), "SettingsFragment")
            .hide(childBottomNavFragments.get(4));
        fragmentTransaction.commit();


        B.mainBottomNavView.setOnNavigationItemSelectedListener(item -> {
            replaceFragment(fragmentManager, item.getItemId());
            return true;
        });


        B.mainBottomNavView.setSelectedItemId(R.id.mainBottomNavHomeBtn);
        updateTabItemBadge(R.id.mainBottomNavConnectingBtn, 100);
    }


    private void replaceFragment(FragmentManager manager, int itemId) {
        int itemIndex = 0;  //  dùng để thay đổi tiêu đề của ActionBar


        switch (itemId) {
            case R.id.mainBottomNavConnectingBtn: {
                itemIndex = 0;
                break;
            }
            case R.id.mainBottomNavCalendarBtn: {
                itemIndex = 1;
                break;
            }
            case R.id.mainBottomNavHomeBtn: {
                itemIndex = 2;
                break;
            }
            case R.id.mainBottomNavNotificationBtn: {
                itemIndex = 3;
                break;
            }
            case R.id.mainBottomNavSettingsBtn: {
                itemIndex = 4;
                break;
            }
        }


        //  khi nhấn cùng 1 nút, không xử lý
        if (itemIndex == currentTabIndex)
            return;


        //  tiến hành replace fragment
        Fragment newFragment = childBottomNavFragments.get(itemIndex);
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.hide(activeFragment).show(newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
        activeFragment = newFragment;



        {
            //  cập nhật TabIndex
            currentTabIndex = itemIndex;

            //  thay đổi tiêu đề của ActionBar
            changeActionBarTitle(itemIndex);

            //  ẩn/hiện txtMonthValue dựa vào itemIndex
            updateMonthValue();

            //  ẩn/hiện searchBar dựa vào itemIndex
            handleSearchBarVisibility(itemIndex);


            hideActionBarAtHomeFragment(itemIndex);
        }
    }

    private void hideActionBarAtHomeFragment(int itemIndex) {
        if (itemIndex == 2) {
            B.mainActionBar.setVisibility(View.GONE);
            StatusBarCompat.translucentStatusBar(this);
        }
        else {
            B.mainActionBar.setVisibility(View.VISIBLE);
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.primaryHeader));
        }
    }

    private void handleSearchBarVisibility(int tabIndex) {
        switch (tabIndex) {
            case 3: {
                B.edtSearchNews.setVisibility(View.VISIBLE);
                break;
            }

            default: {
                B.edtSearchNews.setVisibility(View.GONE);
            }
        }
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
        B.actionBarTitle.setText(mainScreenActionBarBarTitles.get(tabIndex));
    }

    private void updateMonthValue() {
        B.actionBarMonthView.setText(currentTabIndex == 1? currentMonthValue: "");
    }

    /**
     * @param itemId tab item muốn hiển thị | zero-based
     * @param number số lượng muốn hiển thị trên Badge | nếu là 0 thì ẩn
     */
    private void updateTabItemBadge(int itemId, int number) {
        //   khởi tạo BadgeDrawable
        BadgeDrawable drawable = B.mainBottomNavView.getOrCreateBadge(itemId);
        drawable.setBadgeGravity(BadgeDrawable.TOP_END);
        drawable.setBackgroundColor(getResources().getColor(R.color.BadgeBackgroundColor));
        drawable.setVerticalOffset(10);
        drawable.setHorizontalOffset(5);
        drawable.setMaxCharacterCount(3);

        if (number == 0) {
            drawable.setVisible(false);
        }
        else {
            drawable.setNumber(number);
        }
    }



    //  callbacks from interfaces
    @Override
    public void onCalendarMonthValueChanged(String value) {
        currentMonthValue = value;
        updateMonthValue();
    }

    @Override
    public void onChildFragmentAttached(Fragment fragment) {
        /*Log.i("NewsCommonFragment", "onNewFragmentAttached: " + fragment.getClass());
        childFragments.add(fragment);*/
    }

}