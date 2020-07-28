package com.bvu.assistant.viewmodel.retrofit.student_attendance;

public class StudentAttendance {
    private String subjectId;
    private String subjectName;
    private Integer credits;
    private Integer excusedAbsences;
    private Integer unExcusedAbsences;

    public StudentAttendance(String subjectId, String subjectName, Integer credits, Integer excusedAbsences, Integer unExcusedAbsences) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.excusedAbsences = excusedAbsences;
        this.unExcusedAbsences = unExcusedAbsences;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getExcusedAbsences() {
        return excusedAbsences;
    }

    public void setExcusedAbsences(Integer excusedAbsences) {
        this.excusedAbsences = excusedAbsences;
    }

    public Integer getUnExcusedAbsences() {
        return unExcusedAbsences;
    }

    public void setUnExcusedAbsences(Integer unExcusedAbsences) {
        this.unExcusedAbsences = unExcusedAbsences;
    }
}
