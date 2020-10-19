package com.bvu.assistant.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * @param position the position of the item that being binded
     */
    public abstract void onBind(int position);
}