package com.bvu.assistant.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentHomeBinding;
import com.bvu.assistant.databinding.HomeFrmAttendanceItemBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.MainActivity;
import com.bvu.assistant.ui.main.home.functions.HomeFunctionsActivity;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import qiu.niorgai.StatusBarCompat;



public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentViewModel> {
    private static final String TAG = "HomeFragment";
    ArrayList<Entry> charData;



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            StatusBarCompat.translucentStatusBar(getActivity());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        VM = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        B.setViewModel(VM);
        observe();

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).onDirectChildFragmentAttached((ViewGroup)B.getRoot());
        }
    }


    @SuppressLint("SetTextI18n")
    private void observe() {
        String ssid = activity.getIntent().getStringExtra("ssid");

        B.btnViewReceipt.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeFunctionsActivity.class);
            intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_fifthTitle));
            intent.putExtra("ssid", ssid);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        B.btnViewRoadMap.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeFunctionsActivity.class);
            intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_secondTitle));
            intent.putExtra("ssid", ssid);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        B.btnViewExercisingScores.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeFunctionsActivity.class);
            intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_eighthTitle));
            intent.putExtra("ssid", ssid);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        B.btnViewAward.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeFunctionsActivity.class);
            intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_sixthTitle));
            intent.putExtra("ssid", ssid);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        B.btnViewLiability.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeFunctionsActivity.class);
            intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_fourthTitle));
            intent.putExtra("ssid", ssid);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });



        /* getting profile info */
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                VM.getProfileInfo(ssid).observe(getViewLifecycleOwner(), profile -> {
                    if (profile != null) {
                        setAvatar(profile.getName());
                        B.txtStudentName.setText(profile.getName() + " - " + profile.getLearningStatus().getClassName());
                        B.txtStudentDepartment.setText(profile.getLearningStatus().getDepartment());


                        Intent intent = new Intent(activity, HomeFunctionsActivity.class);
                        intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_firstTitle));
                        intent.putExtra("profile", new Gson().toJson(profile));
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        B.btnViewProfile.setOnClickListener(v -> startActivity(intent));

                        Intent officeIntent = new Intent(activity, HomeFunctionsActivity.class);
                        officeIntent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_thirdTitle));
                        officeIntent.putExtra("office365", new Gson().toJson(profile.getPersonalProfile().getBvu365()));
                        officeIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        B.btnViewOffice365.setOnClickListener(v -> startActivity(officeIntent));
                    }
                });
            }
        });

        /* getting attendance info */
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                VM.getAttendanceInfo(ssid).observe(getViewLifecycleOwner(), attendanceInfo -> {
                    if (attendanceInfo != null) {
                        processAttendanceInfo(attendanceInfo);

                        Intent intent = new Intent(activity, HomeFunctionsActivity.class);
                        intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_ninthTitle));
                        intent.putExtra("attendance", new Gson().toJson(attendanceInfo.toArray()));
                        B.btnViewAttendanceInfo.setOnClickListener(v -> startActivity(intent));
                    }
                });
            }
        });

        /* getting Learning Scores */
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                VM.getLearningScores(ssid).observe(getViewLifecycleOwner(), learningScores -> {
                    if (learningScores != null) {
                        processLearningInfo(learningScores);

                        Intent intent = new Intent(activity, HomeFunctionsActivity.class);
                        intent.putExtra(HomeFunctionsActivity.INTENT_KEY, getResources().getString(R.string.homeFrm_grid_seventhTitle));
                        intent.putExtra("learningScores", new Gson().toJson(learningScores));
                        B.btnViewLearningScores.setOnClickListener(v -> startActivity(intent));
                    }
                });
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
    private void processAttendanceInfo(List<Student.AttendanceInfo> dataList) {
        LayoutInflater inflater = getLayoutInflater();
        long delay = 200;
        long duration = 750L;

        for (Student.AttendanceInfo sa : dataList) {
            HomeFrmAttendanceItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.home_frm_attendance_item, null, false);

            /*int absences = sa.getExcusedAbsences() + sa.getUnExcusedAbsences();*/
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

    @SuppressLint("DefaultLocale")
    private void processLearningInfo(Student.LearningScores data) {
        String[] ignoredSubjects = getIgnoredSubjects(data.getSummary());
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        int termIndex = 1;



        Log.d(TAG, "processLearningInfo() returned: " + ignoredSubjects.length + " ignored subjects");


        //  duyêt qua mỗi học kỳ
        for (Student.LearningScores.Term t : data.getTerms()) {
            float average = 0f;
            int totalSubjects = t.getSubjects().size();

            for (Student.LearningScores.Subject s : t.getSubjects()) {
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

    private String[] getIgnoredSubjects(Student.LearningScores.Summary summaryInfo) {
        return summaryInfo.getNotes().split("Điểm ")[1].split(" không tính vào Trung bình chung tích lũy.")[0].split(", ");
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }

}