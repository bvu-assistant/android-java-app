package com.bvu.assistant.ui.main.news.saved;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentSavedNewsBinding;
import com.bvu.assistant.data.repository.room.SqLiteArticleHelper;
import com.bvu.assistant.data.repository.room.SqLiteArticleHelperCallback;
import com.bvu.assistant.ui.base.BaseFragment;
//import com.bvu.assistant.viewmodel.repository.SqLiteArticleHelperKt;

import org.jetbrains.annotations.NotNull;


public class SavedNewsFragment
        extends BaseFragment<FragmentSavedNewsBinding, SavedNewsFragmentViewModel>
        implements SqLiteArticleHelperCallback {


    private void loadSavedArticles(TextView txt) {
        SqLiteArticleHelper helper = new SqLiteArticleHelper(getContext(), this);
        txt.setText(helper.getAllArticles().size() + "");
    }


    @Override
    public void onDatabaseTransactionSuccess(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDatabaseTransactionFailure(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_saved_news;
    }

    @Override
    public int getBindingVariables() {
        return 0;
    }

}