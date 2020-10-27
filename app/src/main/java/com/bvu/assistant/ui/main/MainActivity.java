package com.bvu.assistant.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.ActivityMainBinding;
import com.bvu.assistant.ui.base.BaseActivity;
import com.bvu.assistant.ui.main.connecting.ConnectingFragment;
import com.bvu.assistant.ui.main.home.HomeFragment;
import com.bvu.assistant.ui.main.news.NewsFragment;
import com.bvu.assistant.ui.main.settings.SettingsFragment;
import com.bvu.assistant.ui.main.calendar.CalendarFragment;
import com.bvu.assistant.data.model.interfaces.CommonNewsSearchCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import qiu.niorgai.StatusBarCompat;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> {
    private final String TAG = "MainActivity";

    public ArrayList<Fragment> childBottomNavFragments; //  lưu giữ trạng thái của các fragment con
    private ArrayList<String> mainScreenActionBarBarTitles;     //  listing các tiêu đề của ActionBar
    private ArrayList<Fragment> childNewsCommonFragments;     //  lưu giữ các fragment con (trong NewsCommon) | dùng cho searching
    public Fragment activeFragment;
    private int currentTabIndex = 0;
    private List<Integer> bottomNavItemsId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VM = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        B.setViewModel(VM);

        initAndMapping();
        handleBottomNavBar();
        handleFirebaseInstanceId();
        observe();
    }

    private void observe() {
        VM.bottomNavBadges.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                for (int i = 0, length = integers.size(); i < length; ++i) {
                    updateTabItemBadge(i, integers.get(i));
                }
            }
        });
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

        //  list item id của BottomNavBar
        bottomNavItemsId = new ArrayList<>();
        bottomNavItemsId.add(R.id.mainBottomNavConnectingBtn);
        bottomNavItemsId.add(R.id.mainBottomNavCalendarBtn);
        bottomNavItemsId.add(R.id.mainBottomNavHomeBtn);
        bottomNavItemsId.add(R.id.mainBottomNavNotificationBtn);
        bottomNavItemsId.add(R.id.mainBottomNavSettingsBtn);

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


        //  khởi tạo và thêm tất cả các fragment (của BottomNav) trong Activity này
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
        if (itemIndex == currentTabIndex) {
            ArrayList<Integer> newList = VM.bottomNavBadges.getValue();
            newList.set(itemIndex, new Random().nextInt(101));
            VM.bottomNavBadges.setValue(newList);

            Log.i(TAG, "replaceFragment: " + newList.toString());
            return;
        }


        //  tiến hành replace fragment
        Fragment newFragment = childBottomNavFragments.get(itemIndex);
        FragmentTransaction transaction = manager.beginTransaction();
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
            handleMonthValueVisibility(itemIndex);

            //  ẩn/hiện searchBar dựa vào itemIndex
            handleSearchBarVisibility(itemIndex);

            handleActionBarVisibility(itemIndex);
        }
    }

    private void handleMonthValueVisibility(int itemIndex) {
        MainActivityViewModel.MonthTitle newTitle = VM.monthValue.getValue();

        switch (itemIndex) {
            case 1: {
                newTitle.setVisible(true);
                VM.monthValue.setValue(newTitle);
                break;
            }

            default: {
                newTitle.setVisible(false);
                VM.monthValue.setValue(newTitle);
            }
        }
    }

    private void handleActionBarVisibility(int itemIndex) {
        if (itemIndex == 2) {
            VM.isActionBarShowing.setValue(false);
            StatusBarCompat.translucentStatusBar(this);
        }
        else {
            VM.isActionBarShowing.setValue(true);
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.primaryHeader));
        }
    }

    private void handleSearchBarVisibility(int tabIndex) {
        switch (tabIndex) {
            case 3: {
                VM.isSearchBarShowing.setValue(true);
                break;
            }

            default: {
                VM.isSearchBarShowing.setValue(false);
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
            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        };


        Task<InstanceIdResult> instanceId = FirebaseInstanceId.getInstance().getInstanceId();
        instanceId.addOnCompleteListener(onCompleteListener);
    }

    private void changeActionBarTitle(int tabIndex) {
        VM.actionBarTitle.setValue(mainScreenActionBarBarTitles.get(tabIndex));
    }


    /**
     * @param tabIndex tab item muốn hiển thị | zero-based
     * @param number số lượng muốn hiển thị trên Badge | nếu là 0 thì ẩn
     */
    private void updateTabItemBadge(int tabIndex, int number) {
        //   khởi tạo BadgeDrawable
        BadgeDrawable drawable = B.mainBottomNavView.getOrCreateBadge(bottomNavItemsId.get(tabIndex));
        drawable.setBadgeGravity(BadgeDrawable.TOP_END);
        drawable.setBackgroundColor(getResources().getColor(R.color.BadgeBackgroundColor));
        drawable.setVerticalOffset(10);
        drawable.setHorizontalOffset(5);
        drawable.setMaxCharacterCount(3);

        if (number == 0) {
            drawable.setVisible(false);
        }
        else {
            drawable.setVisible(true);
            drawable.setNumber(number);
        }
    }



    @Override
    public Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }
}