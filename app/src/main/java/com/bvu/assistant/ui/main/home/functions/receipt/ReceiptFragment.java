package com.bvu.assistant.ui.main.home.functions.receipt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentReceiptBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;
import com.bvu.assistant.ui.main.home.HomeFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class ReceiptFragment extends BaseFragment<FragmentReceiptBinding, ReceiptFragmentViewModel> {
    private String ssid;
    private List<Student.ReceiptInfo> dataList;
    private ReceiptRecyclerAdapter adapter;


    public ReceiptFragment(String ssid) {
        this.ssid = ssid;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_receipt;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VM = ViewModelProviders.of(this).get(ReceiptFragmentViewModel.class);
        observe();
    }

    private void observe() {
        dataList = new ArrayList<>();
        adapter = new ReceiptRecyclerAdapter(R.layout.receipt_item_layout, dataList, getContext());
        B.recycler.setAdapter(adapter);
        B.recycler.setLayoutManager(new LinearLayoutManager(activity));


        B.refresher.setColorSchemeColors(
                Color.rgb(0, 138, 230),
                Color.rgb(25, 189, 0),
                Color.rgb(255, 204, 0),
                Color.rgb(245, 0, 53)
        );
        B.refresher.setRefreshing(true);


        VM.getReceiptsInfo(this.ssid)
            .observe(getViewLifecycleOwner(), new Observer<List<Student.ReceiptInfo>>() {
                @Override
                public void onChanged(List<Student.ReceiptInfo> receiptInfo) {
                    dataList.clear();
                    dataList.addAll(receiptInfo);
                    B.recycler.scheduleLayoutAnimation();
                    adapter.notifyDataSetChanged();

                    B.refresher.setRefreshing(false);
                    B.refresher.setEnabled(false);
                }
            });
    }

}
