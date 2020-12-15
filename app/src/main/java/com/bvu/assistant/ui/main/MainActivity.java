package com.bvu.assistant.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.ActivityMainBinding;
import com.bvu.assistant.databinding.FragmentNewsCommonBinding;
import com.bvu.assistant.ui.base.BaseActivity;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.news.common.NewsCommonFragment;
import com.bvu.assistant.utils.StatusBarUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eightbitlab.com.blurview.RenderScriptBlur;
import qiu.niorgai.StatusBarCompat;


@SuppressWarnings("rawtypes")
public class MainActivity
        extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
        implements MainChildFragmentsAttacher {

    private static final String TAG = "MainActivity";

    private ArrayList<String> mainScreenActionBarBarTitles;     //  listing các tiêu đề của ActionBar
    private ArrayList<Pair<BaseFragment, FragmentNewsCommonBinding>> childNewsCommonFragments;     //  lưu giữ các fragment con (trong NewsCommon) | dùng cho searching
    private ArrayList<ViewGroup> directChildFragmentsView;
    private List<Integer> bottomNavItemsId;
    private MainPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true);

        VM = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        B.setViewModel(VM);

        initAndMapping();
        styling();
        handleBottomNavBar();
        handleSearchBarTypingCompleted();
        handleFirebaseInstanceId();
        observe();
    }



    /* initial methods */
    private void observe() {
        VM.bottomNavBadges.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                for (int i = 0, length = integers.size(); i < length; ++i) {
                    updateTabItemBadge(i, integers.get(i));
                }
            }
        });


        VM.fragmentContainerHeight.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 0) {
                    if (VM.bottomNavHeight.getValue() != 0) {
                        changeChildrenFragmentsPadding(integer.intValue());
                    }
                }
            }
        });

        VM.bottomNavHeight.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 0) {
                    if (VM.bottomNavHeight.getValue() != 0) {
                        changeChildrenFragmentsPadding(integer.intValue());
                    }
                }
            }
        });
    }

    private void styling() {
        blurBottomNavBar();
        reSetUpFragmentContainerPadding();
        reSetUpActionBarPadding();
    }

    private void initAndMapping() {
        childNewsCommonFragments = new ArrayList<>();
        directChildFragmentsView = new ArrayList<>();


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
        bottomNavItemsId.add(R.id.mainBottomNavNewsBtn);
        bottomNavItemsId.add(R.id.mainBottomNavSettingsBtn);


        adapter = new MainPagerAdapter(getSupportFragmentManager(), 0, this);
        B.viewPager.setAdapter(adapter);
        B.viewPager.setOffscreenPageLimit(4);
        B.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handleActionBarVisibility(position);
                handleSearchBarVisibility(position);
                handleMonthValueVisibility(position);
                changeActionBarTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    /* styling */
    private void blurBottomNavBar() {
        TypedValue value = new TypedValue();
        getResources().getValue(R.dimen.mainAct_bottomNav_blurRadius, value, true);

        float radius = value.getFloat();
        View decorView = getWindow().getDecorView();
        Drawable windowBackground = decorView.getBackground();

        B.mainBottomNavBounder.setupWith((ViewGroup)B.getRoot())
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(new RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setOverlayColor(getResources().getColor(R.color.main_act_bottomnav_overlay))
            .setHasFixedTransformationMatrix(false);
    }

    private void reSetUpFragmentContainerPadding() {
        B.mainBottomNavBounder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                VM.bottomNavHeight.setValue(B.mainBottomNavBounder.getHeight());
                Log.i(TAG, "onGlobalLayout: " + VM.bottomNavHeight.getValue());
                B.mainBottomNavBounder.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        B.viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                VM.fragmentContainerHeight.setValue(B.viewPager.getHeight());
                B.viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void reSetUpActionBarPadding() {
        int sHeight = StatusBarUtils.getHeight(this);
        if (sHeight > 0) {
            B.mainActionBar.setPadding(0, sHeight + 15, 0, 0);
        }
    }

    private void changeChildrenFragmentsPadding(int padding) {
        for (Pair<BaseFragment, FragmentNewsCommonBinding> m : childNewsCommonFragments) {
            m.second.recycler.setPadding(
                    0,
                    (int)getResources().getDimension(R.dimen.newsCommonFrm_recycler_vertical_padding),
                    0,
                    padding
            );
        }

        for (ViewGroup vg: directChildFragmentsView) {
            vg.setPadding(
                0,
                0,
                0,
                padding
            );
        }
    }



    private void handleBottomNavBar() {

        B.mainBottomNavView.setOnNavigationItemSelectedListener(item -> {
            B.viewPager.setCurrentItem(bottomNavItemsId.indexOf(item.getItemId()), true);
            return true;
        });


        B.mainBottomNavView.setSelectedItemId(R.id.mainBottomNavHomeBtn);
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
            /*StatusBarCompat.translucentStatusBar(this);*/
        }
        else {
            VM.isActionBarShowing.setValue(true);
            /*StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.primaryHeader));*/
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

    private void handleSearchBarTypingCompleted() {
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
                for (Pair<BaseFragment, FragmentNewsCommonBinding> m : childNewsCommonFragments) {
                    if (m.first instanceof NewsCommonFragment)
                        ((NewsCommonFragment) m.first).onTypeComplete(s.toString());
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



    /* overriding parent methods */
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

    @Override
    public void onBackPressed() {
        /*if (B.viewPager.getCurrentItem() != 2) {
            B.mainBottomNavView.getChildAt(2).setEnabled(true);
            B.viewPager.setCurrentItem(2, true);
            return;
        }*/

        super.onBackPressed();
    }



    /* NewsCommon fragments attached */
    @Override
    public void onNewsFragmentAttached(BaseFragment fragment, FragmentNewsCommonBinding B) {
        childNewsCommonFragments.add(Pair.create(fragment, B));
    }

    @Override
    public void onDirectChildFragmentAttached(ViewGroup group) {
        directChildFragmentsView.add(group);
    }

}