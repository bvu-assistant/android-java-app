package com.bvu.assistant.ui.main.home.functions.receipt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.ReceiptItemLayoutBinding;

import java.util.List;


public class ReceiptRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Student.ReceiptInfo> dataSource;
    private int itemLayoutId;
    private Context context;

    public ReceiptRecyclerAdapter(int itemLayoutId, List<Student.ReceiptInfo> dataSource, Context context) {
        this.dataSource = dataSource;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(itemLayoutId, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student.ReceiptInfo info = dataSource.get(position);

        holder.B.txtCost.setText(String.format("%s đ", info.getTotalCost()));
        holder.B.txtBankName.setText(info.getBankName());
        holder.B.txtContent.setText(info.getContent());
        holder.B.txtTransactionTime.setText(info.getTransactionTime());
        holder.B.txtPaidTime.setText(info.getDateOfPayment());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}


class ViewHolder extends RecyclerView.ViewHolder {
    public ReceiptItemLayoutBinding B;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        B = DataBindingUtil.bind(itemView);
    }
}