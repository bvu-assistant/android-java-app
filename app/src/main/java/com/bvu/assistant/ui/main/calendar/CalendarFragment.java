package com.bvu.assistant.ui.main.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.ActivityMainBinding;
import com.bvu.assistant.databinding.FragmentCalendarBinding;
import com.bvu.assistant.databinding.FragmentCalendarDayViewBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.MainActivity;
import com.bvu.assistant.ui.main.MainActivityViewModel;
import com.bvu.assistant.ui.main.calendar.helpers.ExtensionsKt;
import com.kizitonwose.calendarview.model.CalendarDay;
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

import static com.bvu.assistant.ui.main.calendar.helpers.ExtensionsKt.daysOfWeekFromLocale;


public class CalendarFragment extends BaseFragment<FragmentCalendarBinding, CalendarFragmentViewModel> {
    private static final String TAG = "CalendarFragmentTAG";
    DateTimeFormatter monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM");
    LocalDate selectedDate;
    LocalDate toDate;
    int maxDayViewHeight = 0;


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

        DayOfWeek[] daysOfWeek = daysOfWeekFromLocale();
        YearMonth currentMonth = YearMonth.now();
        toDate = LocalDate.now();


        //  Initial setups
        B.calendarView.setup(currentMonth.minusMonths(3), currentMonth.plusMonths(3), daysOfWeek[0]);
        B.calendarView.scrollToMonth(currentMonth);
        Toast.makeText(getContext(), "creating calendar...", Toast.LENGTH_SHORT).show();


        //  Set the height for each DayView
        B.calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                B.calendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                B.calendarView.setDaySize(new Size(B.calendarView.getHeight() / 7, B.calendarView.getHeight() / 6));
            }
        });


        //  Set the view for each day
        B.calendarView.setDayBinder(new DayBinder<DayViewContainer>() {

            @NotNull
            @Override
            public DayViewContainer create(@NotNull View view) {
                return new DayViewContainer(view);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void bind(@NotNull DayViewContainer dayViewContainer, @NotNull CalendarDay calendarDay) {
                dayViewContainer.setDay(calendarDay);

                TextView txtDayText = dayViewContainer.binder.exFiveDayText;
                ConstraintLayout layout = dayViewContainer.binder.exFiveDayLayout;
                RelativeLayout content = dayViewContainer.binder.exFiveDayContent;
                LinearLayout scheduleContainer = dayViewContainer.binder.scheduleContainer;
                txtDayText.setText(calendarDay.getDate().getDayOfMonth() + "");


                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    txtDayText.setTextColor(Color.BLACK);

                    //  Set the red text color if the day is Saturday or Sunday
                    if (calendarDay.getDate().getDayOfWeek().getValue() == 6 ||
                        calendarDay.getDate().getDayOfWeek().getValue() == 7) {
                        txtDayText.setTextColor(Color.RED);
                    }


                    //  Set today background
                    if (calendarDay.getDate().equals(toDate)) {
                        content.setBackgroundResource(R.drawable.example_5_selected_bg);
                    }else {
                        content.setBackgroundResource(R.color.white);
                    }
                }
                else  {
                    ExtensionsKt.setTextColorRes(txtDayText, R.color.calendar_outDate_color);
                    content.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });


        //  When month is change (scrolling)
        B.calendarView.setMonthScrollListener(calendarMonth -> {
            String monthStr = monthTitleFormatter.format(calendarMonth.getYearMonth());
            int yearNum = calendarMonth.getYearMonth().getYear();
            @SuppressLint("DefaultLocale") String title = String.format("%s, năm %d", StringUtils.capitalize(monthStr), yearNum);

            if (activity instanceof MainActivity) {
                MainActivityViewModel mVM = ViewModelProviders.of(activity).get(MainActivityViewModel.class);
                mVM.monthValue.setValue(new MainActivityViewModel.MonthTitle(true, title));
            }

            return null;
        });


        //  Floating ActionButton onClick --> Smooth scroll to current month
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

                    Toast.makeText(getContext(), binder.scheduleContainer.getHeight() + "", Toast.LENGTH_SHORT).show();

                    if (selectedDate != day.getDate()) {
                        LocalDate oldDate = selectedDate;
                        selectedDate = day.getDate();
                        B.calendarView.notifyDateChanged(day.getDate());

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