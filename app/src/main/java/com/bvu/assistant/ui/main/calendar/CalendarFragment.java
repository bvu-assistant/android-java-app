package com.bvu.assistant.ui.main.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentCalendarBinding;
import com.bvu.assistant.databinding.FragmentCalendarDayViewBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.MainActivity;
import com.bvu.assistant.ui.main.MainActivityViewModel;
import com.bvu.assistant.ui.main.calendar.helpers.ExtensionsKt;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;
import com.kizitonwose.calendarview.utils.Size;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static com.bvu.assistant.ui.main.calendar.helpers.ExtensionsKt.daysOfWeekFromLocale;


public class CalendarFragment extends BaseFragment<FragmentCalendarBinding, CalendarFragmentViewModel> {
    private static final String TAG = "CalendarFragmentTAG";

    DateTimeFormatter monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private CalendarAdapter adapter;
    private List<Student.CalendarSchedule> dataList;
    private Map<String, List<Student.CalendarSchedule>> groupedDataList; /* grouped by date */
    LocalDate selectedDate;
    LocalDate toDate;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).onDirectChildFragmentAttached((ViewGroup)B.getRoot());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /* initial the data adapter */
        dataList = new ArrayList<>();
        adapter = new CalendarAdapter(dataList);
        final boolean[] isNormalScheduleReady = {false};
        final boolean[] isTestScheduleReady = {false};


        /* get the ViewModel's instance */
        VM = new ViewModelProvider(this).get(CalendarFragmentViewModel.class);
        String ssid = activity.getIntent().getStringExtra("ssid");



        /* getting schedules info by Retrofit */
        VM.getNormalSchedules(ssid).observe(getViewLifecycleOwner(), new Observer<Student.NormalSchedule>() {
            @Override
            public void onChanged(Student.NormalSchedule normalSchedule) {
                dataList.addAll(normalSchedule.toCalendarSchedules());
                isNormalScheduleReady[0] = true;

                if (isNormalScheduleReady[0] && isTestScheduleReady[0])
                    initCalendar();
            }
        });

        VM.getTestSchedules(ssid).observe(getViewLifecycleOwner(), new Observer<Student.TestSchedule>() {
            @Override
            public void onChanged(Student.TestSchedule testSchedule) {
                dataList.addAll(testSchedule.toCalendarSchedules());
                isTestScheduleReady[0] = true;

                if (isNormalScheduleReady[0] && isTestScheduleReady[0])
                    initCalendar();
            }
        });
    }



    private void initCalendar() {
        /* group the dataList items by Date */
        groupedDataList = dataList.stream().collect(Collectors.groupingBy(Student.CalendarSchedule::getDate));
        DayOfWeek[] daysOfWeek = daysOfWeekFromLocale();
        YearMonth currentMonth = YearMonth.now();
        toDate = LocalDate.now();


        //  Initial setups
        B.calendarView.setup(currentMonth.minusMonths(3), currentMonth.plusMonths(3), daysOfWeek[0]);
        B.calendarView.scrollToMonth(currentMonth);


        //  Set the height for each DayView
        B.calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                B.calendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                B.calendarView.setDaySize(new Size(B.calendarView.getHeight() / 7, B.calendarView.getHeight() / 6));
            }
        });



        //  Set the view for each day
        B.calendarView.setDayBinder(dayBinder);

        //  When month is change (scrolling)
        B.calendarView.setMonthScrollListener(monthScrollListener);



        //  Floating ActionButton onClick --> Smooth scroll to current month
        B.btnScrollToDate.setOnClickListener(v -> {
            B.calendarView.smoothScrollToMonth(currentMonth);
        });
    }

    private DayBinder<DayViewContainer> dayBinder = new DayBinder<DayViewContainer>() {
        @NotNull
        @Override
        public DayViewContainer create(@NotNull View view) {
            return new DayViewContainer(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void bind(@NotNull DayViewContainer dayView, @NotNull CalendarDay calendarDay) {
            /* set the day for ViewHolder */
            dayView.bind(calendarDay);
        }
    };

    private Function1<CalendarMonth, Unit> monthScrollListener = new Function1<CalendarMonth, Unit>() {
        @Override
        public Unit invoke(CalendarMonth calendarMonth) {
            String monthStr = monthTitleFormatter.format(calendarMonth.getYearMonth());
            int yearNum = calendarMonth.getYearMonth().getYear();
            @SuppressLint("DefaultLocale") String title = String.format("%s, năm %d", StringUtils.capitalize(monthStr), yearNum);

            if (activity instanceof MainActivity) {
                MainActivityViewModel mVM = ViewModelProviders.of(activity).get(MainActivityViewModel.class);
                mVM.monthValue.setValue(new MainActivityViewModel.MonthTitle(true, title));
            }
            return null;
        }
    };



    class DayViewContainer extends ViewContainer {
        FragmentCalendarDayViewBinding dayB;

        public DayViewContainer(@NotNull View view) {
            super(view);
            dayB = FragmentCalendarDayViewBinding.bind(view);

        }


        @SuppressLint("SetTextI18n")
        public void bind(CalendarDay day) {
            /* set current date's Text */
            dayB.dateText.setText(day.getDate().getDayOfMonth() + "");


            if (day.getOwner() == DayOwner.THIS_MONTH) {
                dayB.dateText.setTextColor(Color.BLACK);

                //  Set the red text color if the day is Saturday or Sunday
                if (day.getDate().getDayOfWeek().getValue() == 6
                        || day.getDate().getDayOfWeek().getValue() == 7) {
                    dayB.dateText.setTextColor(Color.RED);
                }

                //  Set today background
                dayB.container.setBackgroundResource(day.getDate().equals(toDate) ?
                    R.drawable.example_5_selected_bg : R.color.white);
            }
            else { //  dates that out of this month
                ExtensionsKt.setTextColorRes(dayB.dateText, R.color.calendar_outDate_color);
                dayB.container.setBackgroundColor(getResources().getColor(R.color.white));
            }



            /* get the schedule of current date */
            List<Student.CalendarSchedule> sList = groupedDataList.get(day.getDate().format(dateFormatter));


            if (day.getOwner() == DayOwner.THIS_MONTH
                && sList != null && sList.size() >= 1) {

                /* add the marker to the CalendarView */
                dayB.markerContainer.removeAllViews();

                /* loop through each schedule of the date to add marker */
                for (Student.CalendarSchedule sch : sList) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    TextView item = (TextView) inflater.inflate(R.layout.fragment_calendar_schedule_item_view, null);

                    item.setText(sch.getSubjectName());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0 , 0, 0, 3);
                    item.setLayoutParams(params);

                    /* set marker background tint color */
                    boolean isTestSchedule = sch.getScheduleType() == Student.CalendarSchedule.ScheduleType.Test;
                    item.getBackground().setTint(isTestSchedule ?
                        getResources().getColor(android.R.color.holo_orange_dark) :
                            getResources().getColor(R.color.colorPrimary));

                    dayB.markerContainer.addView(item);
                }


                dayB.container.setOnClickListener(v -> {
                    showScheduleDetail();
                }); /* dayView onClick */

            }   /* day that has schedules */

        } /* bind */


        private void showScheduleDetail() {

        }


    } /* DayViewContainer class */

}