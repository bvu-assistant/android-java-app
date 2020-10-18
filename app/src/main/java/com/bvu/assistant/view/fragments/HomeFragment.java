package com.bvu.assistant.view.fragments;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentHomeBinding;
import com.bvu.assistant.databinding.HomeFrmAttendanceItemBinding;
import com.bvu.assistant.view.activities.MainActivity;
import com.bvu.assistant.viewmodel.retrofit.student_attendance.AttendanceAPI;
import com.bvu.assistant.viewmodel.retrofit.student_attendance.StudentAttendance;
import com.bvu.assistant.viewmodel.retrofit.student_learning_curve.LearningCurveAPI;
import com.bvu.assistant.viewmodel.retrofit.student_learning_curve.StudentLearningCurve;
import com.bvu.assistant.viewmodel.retrofit.student_profile.StudentProfile;
import com.bvu.assistant.viewmodel.retrofit.student_profile.StudentProfileAPI;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import qiu.niorgai.StatusBarCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragmentTAG";
    FragmentHomeBinding B;
    ArrayList<Entry> charData;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assignEvents();

        Activity activity = getActivity();
        Intent intent = activity == null? null: activity.getIntent();
        String ssid = intent == null? "": intent.getStringExtra("ssid");

        getProfile(ssid);
        getAttendanceInfo(ssid);
        getLearningInfo(ssid);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            StatusBarCompat.translucentStatusBar(getActivity());
        }
    }

    void assignEvents() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        for (int i = 0, rowCount = B.functionsBounder.getChildCount(); i < rowCount; ++i) {
            TableRow row = (TableRow) B.functionsBounder.getChildAt(i);

            for (int j = 0, columnCount = row.getChildCount(); j < columnCount; ++j) {
                LinearLayout btn = (LinearLayout) row.getChildAt(j);
                int finalI = i;
                btn.setOnClickListener(v -> {
                    HomeFunctionsFragment f = new HomeFunctionsFragment();

                    MainActivity activity = (MainActivity) getActivity();
                    activity.getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .show(f)
                        .hide(activity.activeFragment)
                        .addToBackStack(null)
                        .commit();
                });
            }
        }
    }


    private void getLearningInfo(String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.login_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LearningCurveAPI api = retrofit.create(LearningCurveAPI.class);
        Log.i(TAG, "onResponse: " + ssid);

        api.get(ssid)
            .enqueue(new Callback<StudentLearningCurve>() {
                @Override
                public void onResponse(Call<StudentLearningCurve> call, Response<StudentLearningCurve> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        StudentLearningCurve result = response.body();
                        processLearningInfo(result);
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to get learning info", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<StudentLearningCurve> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                    Toast.makeText(getContext(), "Failed to get learning info", Toast.LENGTH_SHORT).show();
                }
            });

    }

    private void getAttendanceInfo(String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.login_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AttendanceAPI api = retrofit.create(AttendanceAPI.class);


        api.getAttendance(ssid)
            .enqueue(new Callback<List<StudentAttendance>>() {
                @Override
                public void onResponse(Call<List<StudentAttendance>> call, Response<List<StudentAttendance>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<StudentAttendance> result = response.body();
                        processAttendanceInfo(result);
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to get attendance info", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<StudentAttendance>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t.getCause());
                    Toast.makeText(getContext(), "Failed to get attendance info", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void getProfile(String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://bvu-loginner.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        StudentProfileAPI api = retrofit.create(StudentProfileAPI.class);


        api.getProfile(ssid)
            .enqueue(new Callback<StudentProfile>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<StudentProfile> call, Response<StudentProfile> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        StudentProfile result = response.body();

                        setAvatar(result.getName());
                        B.txtStudentName.setText(result.getName() + " - " + result.getLearningStatus().getClassName());
                        B.txtStudentDepartment.setText(result.getLearningStatus().getDepartment());
                    }
                    else {
                        Toast.makeText(getContext(), "Get profile failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StudentProfile> call, Throwable t) {
                    Toast.makeText(getContext(), "Get profile failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t.getCause());
                }
            });
    }

    private void setAvatar(String name) {
        String host = "https://ui-avatars.com/api/?size=175&background=f0e9e9&color=8e6161&name=";
        Picasso.get().load(host + name).into(B.imvAvatar);
    }


    private void initChart(ArrayList<Entry> entries) {
        charData = new ArrayList<>();

        LineDataSet lineDataSet = new LineDataSet(entries, "Điểm trung bình chung mỗi học kỳ");
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(true);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.learning_curves_gradient_bg));
        lineDataSet.setColor(getResources().getColor(R.color.colorPrimary));
        lineDataSet.setCircleColor(getResources().getColor(R.color.colorPrimary));
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f", value);
            }
        });


        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);


        B.learningCurveChart.setData(lineData);
        B.learningCurveChart.setTouchEnabled(true);
        B.learningCurveChart.setPinchZoom(false);
        B.learningCurveChart.setScaleEnabled(false);


        //  X axis
        B.learningCurveChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        B.learningCurveChart.getAxisRight().setEnabled(false);
        B.learningCurveChart.getXAxis().setGranularityEnabled(true);
        B.learningCurveChart.getXAxis().setAxisMinimum(0.0f);


        //Hide grid
        B.learningCurveChart.getXAxis().setDrawGridLines(false);
        B.learningCurveChart.getAxisLeft().setDrawGridLines(false);
        B.learningCurveChart.getAxisRight().setDrawGridLines(false);

        //  Y axis
        B.learningCurveChart.getAxisLeft().setAxisMinimum(0.1f);
        B.learningCurveChart.getAxisLeft().setAxisMaximum(10.0f);
        B.learningCurveChart.getAxisLeft().setEnabled(false);


        B.learningCurveChart.getLegend().setEnabled(false);
        B.learningCurveChart.getDescription().setText("Học kỳ");
        B.learningCurveChart.setDragEnabled(false);

        B.learningCurveChart.animateX(1000);
        B.learningCurveChart.animateY(1750);
    }



    @SuppressLint("DefaultLocale")
    private void processAttendanceInfo(List<StudentAttendance> dataList) {
        LayoutInflater inflater = getLayoutInflater();
        long delay = 200;
        long duration = 750L;

        for (StudentAttendance sa : dataList) {
            HomeFrmAttendanceItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.home_frm_attendance_item, null, false);


            //  sa.getExcusedAbsences() + sa.getUnExcusedAbsences(); new Random().nextInt(sa.getCredits() + 1)
            int absences = new Random().nextInt(sa.getCredits() + 1);
            int allowedAbsences = Math.round(sa.getCredits());
            float percents = 1.0f * absences / sa.getCredits() * 100;


            itemBinding.txtSubjectName.setText(sa.getSubjectName());
            itemBinding.fraction.setText(String.format("%d/%d", absences, allowedAbsences));
            itemBinding.progress.setProgressWithAnimation(percents, duration, null, delay);
            itemBinding.progress.setProgressBarColor(
                    percents < 50 ?
                    getResources().getColor(R.color.home_frm_attandance_good):
                        percents < 75 ?
                        getResources().getColor(R.color.home_frm_attandance_warning):
                            getResources().getColor(R.color.home_frm_attandance_bad));

            itemBinding.progress.setBackgroundProgressBarColor(percents == 0 ?
                getResources().getColor(R.color.home_frm_attandance_good):
                getResources().getColor(R.color.home_frm_attendance_normal_bg));

            B.attendanceInfoCard.addView(itemBinding.getRoot());
        }
    }


    /*private float getAttendancePercents(StudentAttendance sa) {

    }

    private int getAttendanceProgressBarColor(StudentAttendance sa) {

    }

    private String getAttendanceFraction(StudentAttendance sa) {

    }*/



    @SuppressLint("DefaultLocale")
    private void processLearningInfo(StudentLearningCurve data) {
        String[] ignoredSubjects = getIgnoredSubjects(data.getSummary());
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        int termIndex = 1;



        Log.d(TAG, "processLearningInfo() returned: " + ignoredSubjects.length + " ignored subjects");


        //  duyêt qua mỗi học kỳ
        for (StudentLearningCurve.Term t : data.getTerms()) {
            float average = 0f;
            int totalSubjects = t.getSubjects().size();

            for (StudentLearningCurve.Subject s : t.getSubjects()) {
                try {
                    boolean isIgnored = false;
                    for (String is: ignoredSubjects) {
                        if (s.getName().equals(is)) {
                            --totalSubjects;
                            isIgnored = true;
                            break;
                        }
                    }

                    average += isIgnored? 0f: Float.parseFloat(s.getAveragePoint().toString());
                }
                catch (Exception ex) {
                    average += 0f;
                    --totalSubjects;
                }
            }


            Log.d(TAG, "processLearningInfo() returned: " + average + " - " + totalSubjects);

            if (totalSubjects != 0)
                entries.add(new Entry(termIndex++, (average / totalSubjects)));
        }




        String l = data.getSummary().getBorrowedCredits().split(" - ")[0];
        String r = data.getSummary().getBorrowedCredits().split(" - ")[1];

        String cgpaScores = String.format("CGPA: %s", data.getSummary().getAverageMark());
        String borrowedCredits = String.format("Tín chỉ nợ: %s/%d - %s",
            l, data.getSummary().getTotalCredits(), r
        );

        B.txtCGPA.setText(cgpaScores);
        B.txtBorrowedCredits.setText(borrowedCredits);

        initChart(entries);
    }

    private String[] getIgnoredSubjects(StudentLearningCurve.Summary summaryInfo) {
        return summaryInfo.getNotes().split("Điểm ")[1].split(" không tính vào Trung bình chung tích lũy.")[0].split(", ");
    }


}