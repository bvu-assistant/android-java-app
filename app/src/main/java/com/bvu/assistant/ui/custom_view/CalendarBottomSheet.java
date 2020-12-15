package com.bvu.assistant.ui.custom_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.CalendarDetailBottomSheetBinding;
import com.bvu.assistant.databinding.FragmentCalendarEventItemViewBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import jp.wasabeef.blurry.Blurry;


public class CalendarBottomSheet {

    public static void show(Context context, List<Student.CalendarSchedule> schedules) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CalendarDetailBottomSheetBinding rootB = DataBindingUtil.inflate(inflater, R.layout.calendar_detail_bottom_sheet, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);


        for (Student.CalendarSchedule cs : schedules) {
            FragmentCalendarEventItemViewBinding itemB = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar_event_item_view, null, false);

            /* assign values/events */
            itemB.scheduleSubjectName.setText(cs.getSubjectName());
            itemB.scheduleDate.setText(String.format("Ngày %s", cs.getDate()));
            itemB.schedulePeriod.setText(String.format("Ca %s", cs.getPeriod()));
            itemB.scheduleExactTime.setText(getExactTimeFromPeriod(cs.getPeriod()));

            String firstRoomCharacts = cs.getRoom().substring(0, 1);
            itemB.scheduleRoom.setText(String.format("Phòng %s (%s)", cs.getRoom(), firstRoomCharacts.equals("5")? "Nhà TĐ": "CS" + firstRoomCharacts));

            if (cs instanceof Student.CalendarNormalSchedule) {
                itemB.scheduleTeacher.setText(((Student.CalendarNormalSchedule) cs).getTeacher());
                itemB.scheduleType.setText(((Student.CalendarNormalSchedule) cs).getLearningType());
            }
            if (cs instanceof Student.CalendarTestSchedule) {
                itemB.scheduleTeacher.setText(((Student.CalendarTestSchedule) cs).getNotes());
                itemB.scheduleType.setText(((Student.CalendarTestSchedule) cs).getTestType());
            }


            /* bottom margin */
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 15;
            itemB.container.setLayoutParams(params);


            rootB.eventsContainer.addView(itemB.getRoot());
        }


        bottomSheetDialog.setContentView(rootB.getRoot());
        bottomSheetDialog.show();
    }


    private static String getExactTimeFromPeriod(String period) {
        String start = period.split("->")[0].trim();
        String end = period.split("->")[1].trim();
        if (end.contains("(")) {
            end = end.split("\\(")[0];
        }

        String startHour = getStartHour(Integer.parseInt(start));
        String endHour = getEndHour(Integer.parseInt(end));


        return String.format("%s - %s", startHour, endHour);
    }


    private static String getEndHour(int parseInt) {
        String result = "";

        switch (parseInt) {
            case 1: {
                result = "07:45";
                break;
            }
            case 2: {
                result = "08:30";
                break;
            }
            case 3: {
                result = "09:25";
                break;
            }
            case 4: {
                result = "10:10";
                break;
            }
            case 5: {
                result = "11:05";
                break;
            }
            case 6: {
                result = "11:50";
                break;
            }
            case 7: {
                result = "13:45";
                break;
            }
            case 8: {
                result = "14:30";
                break;
            }
            case 9: {
                result = "15:25";
                break;
            }
            case 10: {
                result = "16:10";
                break;
            }
            case 11: {
                result = "17:05";
                break;
            }
            case 12: {
                result = "17:50";
                break;
            }
            case 13: {
                result = "19:05";
                break;
            }
            case 14: {
                result = "19:50";
                break;
            }
            case 15: {
                result = "20:45";
                break;
            }
            case 16: {
                result = "21:30";
                break;
            }
        }

        return result;
    }

    private static String getStartHour(int start) {
        String result = "";

        switch (start) {
            case 1: {
                result = "07:00";
                break;
            }
            case 2: {
                result = "07:45";
                break;
            }
            case 3: {
                result = "08:40";
                break;
            }
            case 4: {
                result = "09:25";
                break;
            }
            case 5: {
                result = "10:20";
                break;
            }
            case 6: {
                result = "11:05";
                break;
            }
            case 7: {
                result = "13:00";
                break;
            }
            case 8: {
                result = "13:45";
                break;
            }
            case 9: {
                result = "14:40";
                break;
            }
            case 10: {
                result = "15:25";
                break;
            }
            case 11: {
                result = "16:20";
                break;
            }
            case 12: {
                result = "17:05";
                break;
            }
            case 13: {
                    result = "18:20";
                break;
            }
            case 14: {
                result = "19:05";
                break;
            }
            case 15: {
                result = "20:00";
                break;
            }
            case 16: {
                result = "20:45";
                break;
            }
        }

        return result;
    }

}
