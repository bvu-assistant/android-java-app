package com.bvu.assistant.ui.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.ui.main.news.common.NewsFragmentAttachedCallback;
import com.bvu.assistant.utils.NetworkStatusChecker;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity
        implements BaseFragment.callback, NewsFragmentAttachedCallback {

    protected T B;
    protected V VM;


    /**
     *
     * @return the view data binding of this activity layout
     */
    public T getViewDataBinding() {
        return B;
    }


    /**
     *
     * @return the layout id for displaying of this activity
     */
    @LayoutRes
    public abstract int getLayoutId();


    /**
     *
     * @return the data binding variables's id of this activity
     */
    public abstract int getBindingVariables();


    /**
     * hide the keyboard that showing on this activity
     */
    public void hideKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * @return the networking status. True if Mobile or Wifi. Otherwise is false.
     */
    public boolean hasNetwork() {
        return NetworkStatusChecker.isNetworkConnected(getApplicationContext());
    }


    /**
     * @param permission type of @Manifest.permission
     * @return the @permission is granted or not.
     *      If Build version below 23 (Android M), return true
     *      If Build version from 23, return permission granting status
     */
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = DataBindingUtil.setContentView(this, getLayoutId());
        B.setVariable(getBindingVariables(), VM);
        B.setLifecycleOwner(this);
        B.executePendingBindings();

        B.getRoot().setOnClickListener( v -> hideKeyboard());
    }


    /**
     * @param context the context that will be run this intent
     * @return  intent
     */
    public abstract Intent newIntent(Context context);


    /**
     * @param frm the child fragment that has just attached into this activity
     */
    @Override
    public void onFragmentAttached(BaseFragment frm) {

    }


    @Override
    public void onFragmentDetached(String tag) {

    }

}
