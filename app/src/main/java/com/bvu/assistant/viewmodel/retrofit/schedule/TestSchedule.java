package com.bvu.assistant.viewmodel.retrofit.schedule;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class TestSchedule {
    @SerializedName("Term")
    @Expose
    private String term;

    @SerializedName("Schedule")
    @Expose
    private List<TestScheduleDetail> schedules;


    public TestSchedule(String term, List<TestScheduleDetail> schedules) {
        this.term = term;
        this.schedules = schedules;
    }


    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<TestScheduleDetail> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TestScheduleDetail> schedules) {
        this.schedules = schedules;
    }



    public class TestScheduleDetail {
        @SerializedName("Date")
        @Expose
        private String date;

        @SerializedName("Period")
        @Expose
        private String period;

        @SerializedName("Class")
        @Expose
        private String room;

        @SerializedName("TestType")
        @Expose
        private String testType;

        @SerializedName("Subject")
        @Expose
        private String subjectName;

        @SerializedName("Notes")
        @Expose
        private String notes;



        public TestScheduleDetail(String date, String period, String room, String testType, String subjectName, String notes) {
            this.date = date;
            this.period = period;
            this.room = room;
            this.testType = testType;
            this.subjectName = subjectName;
            this.notes = notes;
        }


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
