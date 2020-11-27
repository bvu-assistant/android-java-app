package com.bvu.assistant.ui.main.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentCalendarDayViewBinding;

import java.util.List;


public class CalendarAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    private final List<Student.CalendarSchedule> dataSource;

    public CalendarAdapter(List<Student.CalendarSchedule> dataSource) {
        this.dataSource = dataSource;
    }


    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentCalendarDayViewBinding B = DataBindingUtil.bind(parent);
        return new ScheduleViewHolder(B);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bind(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}


class ScheduleViewHolder extends RecyclerView.ViewHolder {
    private FragmentCalendarDayViewBinding B;

    public ScheduleViewHolder(FragmentCalendarDayViewBinding B) {
        super(B.getRoot());
    }

    public void bind(Student.CalendarSchedule schedule) {

    }
}