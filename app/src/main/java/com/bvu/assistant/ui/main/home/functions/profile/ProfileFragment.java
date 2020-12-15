package com.bvu.assistant.ui.main.home.functions.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.View;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentProfileBinding;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;
import com.bvu.assistant.ui.main.home.functions.profile.child.FamilyProfileFragment;
import com.bvu.assistant.ui.main.home.functions.profile.child.SelfProfileFragment;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, BaseViewModel> {
    private final Student.Profile profileInfo;

    public ProfileFragment(Student.Profile profileInfo) {
        this.profileInfo = profileInfo;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getParentFragmentManager(), 0);
        B.viewPager.setAdapter(adapter);
        B.tabLayout.setupWithViewPager(B.viewPager);
    }



    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final String[] PAGE_TITLES = {"Bản thân", "Gia đình"};

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return position == 0? new SelfProfileFragment(profileInfo): new FamilyProfileFragment(profileInfo);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }
    }



}