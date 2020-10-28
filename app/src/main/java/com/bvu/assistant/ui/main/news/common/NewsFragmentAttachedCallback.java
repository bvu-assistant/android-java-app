package com.bvu.assistant.ui.main.news.common;

import android.util.Log;

import com.bvu.assistant.databinding.FragmentNewsCommonBinding;
import com.bvu.assistant.ui.base.BaseFragment;

public interface NewsFragmentAttachedCallback {
    default void onNewsFragmentAttached(FragmentNewsCommonBinding B) {};
    default void onNewsFragmentDetached(BaseFragment fragment) {};
}
