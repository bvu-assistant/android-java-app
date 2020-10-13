package com.bvu.assistant.view.fragments;

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

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentHomeBinding;
import com.bvu.assistant.viewmodel.retrofit.student_attendance.AttendanceAPI;
import com.bvu.assistant.viewmodel.retrofit.student_attendance.StudentAttendance;
import com.bvu.assistant.viewmodel.retrofit.student_learning_curve.LearningCurveAPI;
import com.bvu.assistant.viewmodel.retrofit.student_learning_curve.StudentLearningCurve;
import com.bvu.assistant.viewmodel.retrofit.student_profile.StudentProfile;
import com.bvu.assistant.viewmodel.retrofit.student_profile.StudentProfileAPI;
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

//        Snackbar.make(B.getRoot(), "Getting home data...", 3000).show();
        assignEvents();

        Activity activity = getActivity();
        Intent intent = activity == null? null: activity.getIntent();
        String ssid = intent == null? "": intent.getStringExtra("ssid");

        getProfile(ssid);
        getAttendanceInfo(ssid);
        getLearningInfo(ssid);
    }

    void assignEvents() {
        B.btnViewProfile.setOnClickListener(v -> {});
        B.btnViewLearningPath.setOnClickListener(v -> {});
        B.btnViewExerciseInfo.setOnClickListener(v -> {});
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void reStyle() {
        B.txtStudentName.setTextColor(Color.WHITE);
        B.txtStudentName.setBackground(null);
        B.txtDepartment.setTextColor(Color.WHITE);
        B.txtDepartment.setBackground(null);


        B.txtProfile.setTextColor(Color.BLACK);
        B.txtProfile.setBackground(null);
        B.txtLearningPath.setTextColor(Color.BLACK);
        B.txtLearningPath.setBackground(null);
        B.txtExercise.setTextColor(Color.BLACK);
        B.txtExercise.setBackground(null);

        B.imvProfile.setForeground(null);
        B.imvLearningPath.setForeground(null);
        B.imvExercise.setForeground(null);
    }


    private void getLearningInfo(String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bvu-loginner.herokuapp.com")
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
                .baseUrl("http://bvu-loginner.herokuapp.com")
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

                        Picasso.get().load("https://i.pravatar.cc/100").into(B.imvAvatar);
                        B.txtStudentName.setText(result.getName() + " - " + result.getLearningStatus().getClassName());
                        B.txtDepartment.setText(result.getLearningStatus().getDepartment());
//                        reStyle();
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



    void initChart(ArrayList<Entry> entries) {
        charData = new ArrayList<>();

        LineDataSet lineDataSet = new LineDataSet(entries, "Điểm trung bình chung mỗi học kỳ");
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(true);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.learning_curves_gradient_bg));
        lineDataSet.setColor(getResources().getColor(R.color.orange_800));
        lineDataSet.setCircleColor(getResources().getColor(R.color.orange_800));
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value);
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
        B.learningCurveChart.getXAxis().setAxisMinimum(1.0f);


        //Hide grid
        B.learningCurveChart.getXAxis().setDrawGridLines(false);
        B.learningCurveChart.getAxisLeft().setDrawGridLines(false);
        B.learningCurveChart.getAxisRight().setDrawGridLines(false);

        //  Y axis
        B.learningCurveChart.getAxisLeft().setAxisMinimum(1.0f);
        B.learningCurveChart.getAxisLeft().setAxisMaximum(10.0f);
        B.learningCurveChart.getAxisLeft().setEnabled(false);


        B.learningCurveChart.getLegend().setEnabled(false);
        B.learningCurveChart.getDescription().setText("Học kỳ");
        B.learningCurveChart.setDragEnabled(false);
        B.learningCurveChart.invalidate();
    }


    void processAttendanceInfo(List<StudentAttendance> dataList) {
        for (StudentAttendance sa : dataList) {
            TableRow row = new TableRow(getContext());


            TextView txtSubjectName = new TextView(row.getContext());
            txtSubjectName.setText(sa.getSubjectName());
            txtSubjectName.setMaxLines(2);
            txtSubjectName.setEllipsize(TextUtils.TruncateAt.END);
            row.addView(txtSubjectName);
            LayoutParams layoutParams = (LinearLayout.LayoutParams)txtSubjectName.getLayoutParams();
            layoutParams.width = 100;
            txtSubjectName.setPadding(20, 0, 10, 0);
            txtSubjectName.setLayoutParams(layoutParams);


            TextView txtExcusedAbsences = new TextView(row.getContext());
            txtExcusedAbsences.setText(sa.getExcusedAbsences() + "");
            txtExcusedAbsences.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(txtExcusedAbsences);

            TextView txtUnExcusedAbsences = new TextView(row.getContext());
            txtUnExcusedAbsences.setText(sa.getUnExcusedAbsences() + "");
            txtUnExcusedAbsences.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            row.addView(txtUnExcusedAbsences);


            row.setHorizontalScrollBarEnabled(true);
            row.setPadding(0, 10, 0, 10);
            //  row.setBackgroundResource(R.drawable.attendance_table_bg);
            B.tableAttendance.addView(row);
        }
    }

    void processLearningInfo(StudentLearningCurve data) {
        int termIndex = 1;
        ArrayList<Entry> entries = new ArrayList<>();
        String[] ignoredSubjects = getIgnoredSubjects(data.getSummary());


        Log.d(TAG, "processLearningInfo() returned: " + ignoredSubjects.length + " ignored subjects");


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


        initChart(entries);
    }

    String[] getIgnoredSubjects(StudentLearningCurve.Summary summaryInfo) {
        return summaryInfo.getNotes().split("Điểm ")[1].split(" không tính vào Trung bình chung tích lũy.")[0].split(", ");
    }

}