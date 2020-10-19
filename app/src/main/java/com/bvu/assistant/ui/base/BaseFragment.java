package com.bvu.assistant.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {
    interface callback {
        void onFragmentAttached(BaseFragment frm);
        void onFragmentDetached(String tag);
    }

    protected BaseActivity activity;
    protected T B;    //  ViewDataBinding
    protected V VM;   //  ViewModel


    /**
     *
     * @return the BaseActivity that hosted this fragment
     */
    public BaseActivity getBaseActivity() {
        return activity;
    }

    /**
     *
     * @return the view data binding of this fragment layout
     */
    public T getViewDataBinding() {
        return B;
    }

    /**
     *
     * @return the layout id for displaying of this fragment
     */
    @LayoutRes
    public abstract int getLayoutId();


    /**
     *
     * @return the data binding variables's id of this fragment
     */
    public abstract int getBindingVariables();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
            activity.onFragmentAttached(this);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        B = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        B.getRoot().setOnClickListener( v -> hideKeyboard());
        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        B.setVariable(getBindingVariables(), VM);
        B.setLifecycleOwner(this);
        B.executePendingBindings();
    }



    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public boolean hasNetwork() {
        return activity != null && activity.hasNetwork();
    }

}
