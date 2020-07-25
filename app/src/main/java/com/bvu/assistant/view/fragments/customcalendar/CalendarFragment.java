package com.bvu.assistant.view.fragments.customcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.Example5FragmentBinding;
import com.bvu.assistant.databinding.FragmentCalendarBinding;
import com.bvu.assistant.databinding.FragmentCalendarDayViewBinding;
import com.bvu.assistant.view.fragments.customcalendar.helpers.ExtensionsKt;
import com.bvu.assistant.viewmodel.interfaces.MainActivityMonthViewChanger;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.bvu.assistant.view.fragments.customcalendar.helpers.ExtensionsKt.daysOfWeekFromLocale;


public class CalendarFragment extends Fragment {
    FragmentCalendarBinding B;
    MainActivityMonthViewChanger mMainActMonthViewChanger;
    private DateTimeFormatter monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM");
    StudentScheduleAdapter dataAdapter;
    List<StudentSchedule> dataSource;
    LocalDate selectedDate;
    LocalDate toDate;



    public CalendarFragment() {
        super(R.layout.fragment_calendar);
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActMonthViewChanger = (MainActivityMonthViewChanger) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActMonthViewChanger = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        B = FragmentCalendarBinding.bind(view);

//        dataAdapter = new StudentScheduleAdapter(getContext(), dataSource);
//        B.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
//        B.recyclerView.setAdapter(dataAdapter);
//        dataAdapter.notifyDataSetChanged();


        DayOfWeek[] daysOfWeek = daysOfWeekFromLocale();
        YearMonth currentMonth = YearMonth.now();
        toDate = LocalDate.now();

        B.calendarView.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek[0]);
        B.calendarView.scrollToMonth(currentMonth);

//        Toast.makeText(getContext(), currentMonth.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), toDate.toString(), Toast.LENGTH_SHORT).show();


        //  Set the height for each DayView
        B.calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                B.calendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                B.calendarView.setDayHeight(B.calendarView.getHeight() / 6);
            }
        });



        B.calendarView.setDayBinder(new DayBinder<DayViewContainer>() {

            @NotNull
            @Override
            public DayViewContainer create(@NotNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NotNull DayViewContainer dayViewContainer, @NotNull CalendarDay calendarDay) {
                dayViewContainer.setDay(calendarDay);

                TextView txtDayText = dayViewContainer.binder.exFiveDayText;
                ConstraintLayout layout = dayViewContainer.binder.exFiveDayLayout;
                FrameLayout content = dayViewContainer.binder.exFiveDayContent;
                txtDayText.setText(calendarDay.getDate().getDayOfMonth() + "");


                View flightTopView = dayViewContainer.binder.exFiveDayFlightTop;
                View flightBottomView = dayViewContainer.binder.exFiveDayFlightBottom;
                flightTopView.setBackground(null);
                flightBottomView.setBackground(null);



                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    txtDayText.setTextColor(Color.BLACK);


                    if (calendarDay.getDate().equals(toDate)) {
                        content.setBackgroundResource(R.drawable.example_5_selected_bg);
                    }


                    if (selectedDate == calendarDay.getDate()) {
                        Toast.makeText(getContext(), "passed", Toast.LENGTH_SHORT).show();
                        content.setBackgroundResource(R.drawable.example_5_selected_bg);
                    }
                    else {
                        content.setBackgroundResource(R.color.white);
                    }
                }
                else  {
                    ExtensionsKt.setTextColorRes(txtDayText, R.color.calendar_outDate_color);
                    content.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });



        B.calendarView.setMonthScrollListener(calendarMonth -> {
            String monthStr = monthTitleFormatter.format(calendarMonth.getYearMonth());
            int yearNum = calendarMonth.getYearMonth().getYear();
            @SuppressLint("DefaultLocale") String title = String.format("%s, năm %d", StringUtils.capitalize(monthStr), yearNum);
            mMainActMonthViewChanger.changeValue(title);

            return null;
        });


        B.btnScrollToDate.setOnClickListener(v -> {
            B.calendarView.smoothScrollToMonth(currentMonth);
        });
    }



    class DayViewContainer extends ViewContainer {
        CalendarDay day;
        FragmentCalendarDayViewBinding binder;


        public DayViewContainer(@NotNull View view) {
            super(view);
            binder = FragmentCalendarDayViewBinding.bind(view);

            view.setOnClickListener(v -> {
                if (day.getOwner() == DayOwner.THIS_MONTH) {
                    if (selectedDate != day.getDate()) {
                        LocalDate oldDate = selectedDate;
                        selectedDate = day.getDate();
                        B.calendarView.notifyDateChanged(day.getDate());


                        Toast.makeText(getContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show();


                        if (oldDate != null) {
                            B.calendarView.notifyDateChanged(oldDate);
                        }
                    }
                }
            });
        }


        public CalendarDay getDay() {
            return day;
        }

        public void setDay(CalendarDay day) {
            this.day = day;
        }
    }

}