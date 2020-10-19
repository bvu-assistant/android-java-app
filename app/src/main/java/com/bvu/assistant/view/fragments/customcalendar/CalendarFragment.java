package com.bvu.assistant.view.fragments.customcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentCalendarBinding;
import com.bvu.assistant.databinding.FragmentCalendarDayViewBinding;
import com.bvu.assistant.model.Student;
import com.bvu.assistant.view.fragments.customcalendar.helpers.ExtensionsKt;
import com.bvu.assistant.viewmodel.interfaces.MainActivityMonthViewChanger;
import com.bvu.assistant.viewmodel.retrofit.schedule.ScheduleAPI;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bvu.assistant.view.fragments.customcalendar.helpers.ExtensionsKt.daysOfWeekFromLocale;


public class CalendarFragment extends Fragment {
    private static final String TAG = "CalendarFragmentTAG";
    FragmentCalendarBinding B;
    MainActivityMonthViewChanger mMainActMonthViewChanger;
    DateTimeFormatter monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM");
    StudentScheduleAdapter dataAdapter;
    List<StudentSchedule> dataSource;
    LocalDate selectedDate;
    LocalDate toDate;
    int maxDayViewHeight = 0;



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


        DayOfWeek[] daysOfWeek = daysOfWeekFromLocale();
        YearMonth currentMonth = YearMonth.now();
        toDate = LocalDate.now();


        //  Initial setups
        B.calendarView.setupAsync(currentMonth.minusMonths(3), currentMonth.plusMonths(3), daysOfWeek[0], () -> {
            B.calendarView.scrollToMonth(currentMonth);
            Toast.makeText(getContext(), "creating calendar...", Toast.LENGTH_SHORT).show();
            return null;
        });


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
            mMainActMonthViewChanger.onCalendarMonthValueChanged(title);

            return null;
        });


        //  Floating ActionButton onClick --> Smooth scroll to current month
        B.btnScrollToDate.setOnClickListener(v -> {
            B.calendarView.smoothScrollToMonth(currentMonth);
        });
    }


    @Override
    public void onResume() {
        super.onResume();

//        String ssid = getActivity().getIntent().getStringExtra("ssid");
//        getTesScheduleResponse(ssid);

//        Toast.makeText(getContext(), "Calendar fragment", Toast.LENGTH_SHORT).show();
    }


    private void getTesScheduleResponse(String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.fetch_calendar_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.i(TAG, "getTesScheduleResponse: " + ssid);
        ScheduleAPI scheduleAPI = retrofit.create(ScheduleAPI.class);


        scheduleAPI.get(ssid)
                .enqueue(new Callback<Student.TestSchedule>() {
                    @Override
                    public void onResponse(Call<Student.TestSchedule> call, Response<Student.TestSchedule> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "onResponse() returned: " + response.body().getSchedules().get(0).getSubjectName());
                            return;
                        }

                        Toast.makeText(getContext(), "Có lỗi trong quá trình xử lý thông tin lịch", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Student.TestSchedule> call, Throwable t) {
                        Toast.makeText(getContext(), "Có lỗi trong quá trình xử lý thông tin lịch", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getMessage() + t + call);
                    }
                });
    }

    private void updateCalendarForData() {

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