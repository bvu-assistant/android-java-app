package com.bvu.assistant.view.fragments.customcalendar;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentCalendarEventItemViewBinding;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class StudentScheduleAdapter extends RecyclerView.Adapter<StudentScheduleViewHolder> {
    private List<StudentSchedule> dataList;
    private Context context;

    public StudentScheduleAdapter( Context context, List<StudentSchedule> dataList) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public StudentScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentScheduleViewHolder(
                FragmentCalendarEventItemViewBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }


    @Override
    public void onBindViewHolder(@NonNull StudentScheduleViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

}



class StudentScheduleViewHolder extends RecyclerView.ViewHolder {
    private FragmentCalendarEventItemViewBinding binder;
    private DateTimeFormatter formatter;

    public StudentScheduleViewHolder(FragmentCalendarEventItemViewBinding binder) {
        super(binder.getRoot());
        this.binder = binder;
        this.formatter = DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm");
    }

    public void bind(StudentSchedule schedule) {
        setTimeView(schedule);
        binder.scheduleRoom.setText(schedule.getRoom());
        binder.schedulePeriod.setText(schedule.getPeriod());
        binder.scheduleSubjectName.setText(schedule.getSubjectName());
        binder.scheduleTeacher.setText(schedule.getTeacher());
    }

    private void setTimeView(StudentSchedule schedule) {
        binder.scheduleRoom.setText(formatter.format(schedule.getTime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binder.scheduleRoom.setBackgroundColor(itemView.getContext().getColor(R.color.bvuColor));
        }
    }

}