package com.bvu.assistant.viewmodel.retrofit.student_learning_curve;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class StudentLearningCurve {
    @SerializedName("ActualTable")
    @Expose
    private ArrayList<Term> terms;

    @SerializedName("SummaryTable")
    @Expose
    private Summary summary;


    public StudentLearningCurve(ArrayList<Term> terms, Summary summary) {
        this.terms = terms;
        this.summary = summary;
    }


    public ArrayList<Term> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<Term> terms) {
        this.terms = terms;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public class Term {
        @SerializedName("Term")
        @Expose
        String termName;

        @SerializedName("Subjects")
        @Expose
        List<Subject> subjects;


        public Term(String termName, List<Subject> subjects) {
            this.termName = termName;
            this.subjects = subjects;
        }

        public String getTermName() {
            return termName;
        }

        public void setTermName(String termName) {
            this.termName = termName;
        }

        public List<Subject> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<Subject> subjects) {
            this.subjects = subjects;
        }
    }

    public class Subject {
        private String Name;
        private String Class;
        private Integer Credits;
        private Object DiligencePoint;
        private Object MidTermPoint;
        private Object FinalPoint;
        private Object AveragePoint;
        private String Ranked;
        private String Notes;

        public Subject(String name, String aClass, Integer credits, Object diligencePoint, Object midTermPoint, Object finalPoint, Object averagePoint, String ranked, String notes) {
            Name = name;
            Class = aClass;
            Credits = credits;
            DiligencePoint = diligencePoint;
            MidTermPoint = midTermPoint;
            FinalPoint = finalPoint;
            AveragePoint = averagePoint;
            Ranked = ranked;
            Notes = notes;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        @NonNull
        public String getClassName() {
            return Class;
        }

        public void setClass(String aClass) {
            Class = aClass;
        }

        public Integer getCredits() {
            return Credits;
        }

        public void setCredits(Integer credits) {
            Credits = credits;
        }

        public Object getDiligencePoint() {
            return DiligencePoint;
        }

        public void setDiligencePoint(Object diligencePoint) {
            DiligencePoint = diligencePoint;
        }

        public Object getMidTermPoint() {
            return MidTermPoint;
        }

        public void setMidTermPoint(Object midTermPoint) {
            MidTermPoint = midTermPoint;
        }

        public Object getFinalPoint() {
            return FinalPoint;
        }

        public void setFinalPoint(Object finalPoint) {
            FinalPoint = finalPoint;
        }

        public Object getAveragePoint() {
            return AveragePoint;
        }

        public void setAveragePoint(Object averagePoint) {
            AveragePoint = averagePoint;
        }

        public String getRanked() {
            return Ranked;
        }

        public void setRanked(String ranked) {
            Ranked = ranked;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }
    }
    
    public class Summary {
        private Integer TotalCredits;
        private String AverageMark;
        private String BorrowedCredits;
        private String GraduatingRank;
        private String Notes;

        public Summary(Integer totalCredits, String averageMark, String borrowedCredits, String graduatingRank, String notes) {
            TotalCredits = totalCredits;
            AverageMark = averageMark;
            BorrowedCredits = borrowedCredits;
            GraduatingRank = graduatingRank;
            Notes = notes;
        }

        public Integer getTotalCredits() {
            return TotalCredits;
        }

        public void setTotalCredits(Integer totalCredits) {
            TotalCredits = totalCredits;
        }

        public String getAverageMark() {
            return AverageMark;
        }

        public void setAverageMark(String averageMark) {
            AverageMark = averageMark;
        }

        public String getBorrowedCredits() {
            return BorrowedCredits;
        }

        public void setBorrowedCredits(String borrowedCredits) {
            BorrowedCredits = borrowedCredits;
        }

        public String getGraduatingRank() {
            return GraduatingRank;
        }

        public void setGraduatingRank(String graduatingRank) {
            GraduatingRank = graduatingRank;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }
    }
}

