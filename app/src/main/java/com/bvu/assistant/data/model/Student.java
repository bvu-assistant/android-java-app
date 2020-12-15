package com.bvu.assistant.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Student {

    public static class Profile {
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("learningStatus")
        @Expose
        private LearningStatus learningStatus;

        @SerializedName("personalProfile")
        @Expose
        private PersonalProfile personalProfile;

        @SerializedName("familyProfile")
        @Expose
        private List<FamilyProfile.Relative> familyProfile;


        public Profile(String name, LearningStatus learningStatus, PersonalProfile personalProfile, List<FamilyProfile.Relative> familyProfile) {
            this.name = name;
            this.learningStatus = learningStatus;
            this.personalProfile = personalProfile;
            this.familyProfile = familyProfile;
        }


        public String getName() {
            return name;
        }

        public LearningStatus getLearningStatus() {
            return learningStatus;
        }

        public PersonalProfile getPersonalProfile() {
            return personalProfile;
        }

        public List<FamilyProfile.Relative> getFamilyProfile() {
            return familyProfile;
        }

        
        
        public static class LearningStatus {
            private String status;
            private String session;
            private String gender;
            private String name;

            private String educatingRank;
            private String educatingType;
            private String major;
            private String mainMajor;
            private String department;

            @SerializedName("class")
            @Expose
            private String className;
            private String position;
            private String youthActivity;

            public LearningStatus(String status, String session, String gender, String name, String educatingRank, String educatingType, String major, String mainMajor, String department, String className, String position, String youthActivity) {
                this.status = status;
                this.session = session;
                this.gender = gender;
                this.name = name;
                this.educatingRank = educatingRank;
                this.educatingType = educatingType;
                this.major = major;
                this.mainMajor = mainMajor;
                this.department = department;
                this.className = className;
                this.position = position;
                this.youthActivity = youthActivity;
            }

            
            public String getStatus() {
                return status;
            }

            public String getSession() {
                return session;
            }

            public String getGender() {
                return gender;
            }

            public String getName() {
                return name;
            }

            public String getEducatingRank() {
                return educatingRank;
            }

            public String getEducatingType() {
                return educatingType;
            }

            public String getMajor() {
                return major;
            }

            public String getMainMajor() {
                return mainMajor;
            }

            public String getDepartment() {
                return department;
            }

            public String getClassName() {
                return className;
            }

            public String getPosition() {
                return position;
            }

            public String getYouthActivity() {
                return youthActivity;
            }
        }

        public static class PersonalProfile {
            private Birth birth;
            private PID pid;
            private Prioritize prioritize;
            private Nationality nationality;
            private Contact contact;
            private BVU365 bvu365;
            private Gang gang;

            public PersonalProfile(Birth birth, PID pid, Prioritize prioritize, Nationality nationality, Contact contact, BVU365 bvu365, Gang gang) {
                this.birth = birth;
                this.pid = pid;
                this.prioritize = prioritize;
                this.nationality = nationality;
                this.contact = contact;
                this.bvu365 = bvu365;
                this.gang = gang;
            }

            public PersonalProfile.Birth getBirth() {
                return birth;
            }

            public PersonalProfile.PID getPid() {
                return pid;
            }

            public PersonalProfile.Prioritize getPrioritize() {
                return prioritize;
            }

            public PersonalProfile.Nationality getNationality() {
                return nationality;
            }

            public PersonalProfile.Contact getContact() {
                return contact;
            }

            public PersonalProfile.BVU365 getBvu365() {
                return bvu365;
            }

            public PersonalProfile.Gang getGang() {
                return gang;
            }

            public class Birth {
                private String date;
                private String place;

                public Birth(String date, String place) {
                    this.date = date;
                    this.place = place;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getPlace() {
                    return place;
                }

                public void setPlace(String place) {
                    this.place = place;
                }
            }

            public class PID {
                private String number;
                private String issueDate;
                private String issuePlace;

                public PID(String number, String issueDate, String issuePlace) {
                    this.number = number;
                    this.issueDate = issueDate;
                    this.issuePlace = issuePlace;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getIssueDate() {
                    return issueDate;
                }

                public void setIssueDate(String issueDate) {
                    this.issueDate = issueDate;
                }

                public String getIssuePlace() {
                    return issuePlace;
                }

                public void setIssuePlace(String issuePlace) {
                    this.issuePlace = issuePlace;
                }
            }

            public class Prioritize {
                private String region;
                private String object;
                private String policyArea;

                public Prioritize(String region, String object, String policyArea) {
                    this.region = region;
                    this.object = object;
                    this.policyArea = policyArea;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public String getObject() {
                    return object;
                }

                public void setObject(String object) {
                    this.object = object;
                }

                public String getPolicyArea() {
                    return policyArea;
                }

                public void setPolicyArea(String policyArea) {
                    this.policyArea = policyArea;
                }
            }

            public class Nationality {
                private String nation;
                private String religion;

                public Nationality(String nation, String religion) {
                    this.nation = nation;
                    this.religion = religion;
                }

                public String getNation() {
                    return nation;
                }

                public void setNation(String nation) {
                    this.nation = nation;
                }

                public String getReligion() {
                    return religion;
                }

                public void setReligion(String religion) {
                    this.religion = religion;
                }
            }

            public class Contact {
                private Contact.Address address;
                private String phone;
                private String email;
                private String _2ndPhone;
                private String _3rdPhone;

                public class Address {
                    private String houseHold;
                    private String contacting;

                    public Address(String houseHold, String contacting) {
                        this.houseHold = houseHold;
                        this.contacting = contacting;
                    }

                    public String getHouseHold() {
                        return houseHold;
                    }

                    public void setHouseHold(String houseHold) {
                        this.houseHold = houseHold;
                    }

                    public String getContacting() {
                        return contacting;
                    }

                    public void setContacting(String contacting) {
                        this.contacting = contacting;
                    }
                }

                public Contact.Address getAddress() {
                    return address;
                }

                public void setAddress(Contact.Address address) {
                    this.address = address;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String get_2ndPhone() {
                    return _2ndPhone;
                }

                public void set_2ndPhone(String _2ndPhone) {
                    this._2ndPhone = _2ndPhone;
                }

                public String get_3rdPhone() {
                    return _3rdPhone;
                }

                public void set_3rdPhone(String _3rdPhone) {
                    this._3rdPhone = _3rdPhone;
                }
            }

            public class BVU365 {
                private String mail;
                private String password;

                public BVU365(String mail, String password) {
                    this.mail = mail;
                    this.password = password;
                }

                public String getMail() {
                    return mail;
                }

                public void setMail(String mail) {
                    this.mail = mail;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }
            }

            public class Gang {
                private String dateOfDelegation;
                private String dateOfParty;

                public Gang(String dateOfDelegation, String dateOfParty) {
                    this.dateOfDelegation = dateOfDelegation;
                    this.dateOfParty = dateOfParty;
                }

                public String getDateOfDelegation() {
                    return dateOfDelegation;
                }

                public void setDateOfDelegation(String dateOfDelegation) {
                    this.dateOfDelegation = dateOfDelegation;
                }

                public String getDateOfParty() {
                    return dateOfParty;
                }

                public void setDateOfParty(String dateOfParty) {
                    this.dateOfParty = dateOfParty;
                }
            }
        }

        public static class FamilyProfile {
            private List<FamilyProfile.Relative> relativeList;

            public FamilyProfile(List<FamilyProfile.Relative> relativeList) {
                this.relativeList = relativeList;
            }

            public List<FamilyProfile.Relative> getRelativeList() {
                return relativeList;
            }

            public void setRelativeList(List<FamilyProfile.Relative> relativeList) {
                this.relativeList = relativeList;
            }


            public static class Relative {
                private String name;
                private String relation;
                private String dateOfBirth;
                private String nationality;
                private String job;
                private String phone;

                public Relative(String name, String relation, String dateOfBirth, String nationality, String job, String phone) {
                    this.name = name;
                    this.relation = relation;
                    this.dateOfBirth = dateOfBirth;
                    this.nationality = nationality;
                    this.job = job;
                    this.phone = phone;
                }

                
                public String getName() {
                    return name;
                }

                public String getRelation() {
                    return relation;
                }

                public String getDateOfBirth() {
                    return dateOfBirth;
                }

                public String getNationality() {
                    return nationality;
                }

                public String getJob() {
                    return job;
                }

                public String getPhone() {
                    return phone;
                }
            }
        }
    }



    public static class LearningScores {
        @SerializedName("ActualTable")
        @Expose
        private ArrayList<Term> terms;

        @SerializedName("SummaryTable")
        @Expose
        private Summary summary;


        public LearningScores(ArrayList<Term> terms, Summary summary) {
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

    public static class ExercisingScore {
        @Expose
        private String term;
        @Expose
        private Rank rank;
        @Expose
        private List<Bonus> bonus;


        public String getTerm() {
            return term;
        }

        public Rank getRank() {
            return rank;
        }

        public List<Bonus> getBonus() {
            return bonus;
        }


        public static class Rank {
            @Expose
            private float points;
            @Expose
            private String rank;

            public float getPoints() {
                return points;
            }

            public String getRank() {
                return rank;
            }
        }

        public static class Bonus {
            @Expose
            private String date;
            @Expose
            private String gained;
            @Expose
            private String details;

            public String getDate() {
                return date;
            }

            public String getGained() {
                return gained;
            }

            public String getDetails() {
                return details;
            }
        }
    }

    public static class AttendanceInfo {
        private String subjectId;
        private String subjectName;
        private Integer credits;
        private Integer excusedAbsences;
        private Integer unExcusedAbsences;

        public AttendanceInfo(String subjectId, String subjectName, Integer credits, Integer excusedAbsences, Integer unExcusedAbsences) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
            this.credits = credits;
            this.excusedAbsences = excusedAbsences;
            this.unExcusedAbsences = unExcusedAbsences;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public Integer getCredits() {
            return credits;
        }

        public Integer getExcusedAbsences() {
            return excusedAbsences;
        }

        public Integer getUnExcusedAbsences() {
            return unExcusedAbsences;
        }
    }

    public static class LiabilityInfo {
        @Expose
        private String Term;
        @Expose
        private String Liability;

        public String getTerm() {
            return Term;
        }

        public String getLiability() {
            return Liability;
        }
    }

    public static class RoadmapInfo {
        @Expose
        @SerializedName("analysisInfo")
        private AnalysisInfo analysisInfo;
        @Expose
        @SerializedName("terms")
        private List<Term> terms;


        public AnalysisInfo getAnalysisInfo() {
            return analysisInfo;
        }

        public List<Term> getTerms() {
            return terms;
        }


        public static class AnalysisInfo {
            @Expose
            private int totalSubjects;
            @Expose
            private int requiredSubjects;
            @Expose
            private int electiveSubjects;

            public int getTotalSubjects() {
                return totalSubjects;
            }

            public int getRequiredSubjects() {
                return requiredSubjects;
            }

            public int getElectiveSubjects() {
                return electiveSubjects;
            }
        }

        public static class Term {
            @Expose
            private String term;
            @Expose
            private List<Subject> requiredSubjects;
            @Expose
            private List<Subject> electiveSubjects;


            public String getTerm() {
                return term;
            }

            public List<Subject> getRequiredSubjects() {
                return requiredSubjects;
            }

            public List<Subject> getElectiveSubjects() {
                return electiveSubjects;
            }
        }

        public static class Subject {
            @Expose
            @SerializedName("subjectId")
            private String subjectId;
            @Expose
            @SerializedName("subjectName")
            private String subjectName;
            @Expose
            @SerializedName("courseId")
            private String courseId;
            @Expose
            @SerializedName("totalCredits")
            private int totalCredits;
            @Expose
            @SerializedName("practiceLessons")
            private int practiceLessons;
            @Expose
            @SerializedName("theoryLessons")
            private int theoryLessons;

            public String getSubjectId() {
                return subjectId;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public String getCourseId() {
                return courseId;
            }

            public int getTotalCredits() {
                return totalCredits;
            }

            public int getPracticeLessons() {
                return practiceLessons;
            }

            public int getTheoryLessons() {
                return theoryLessons;
            }
        }
    }


    public static class TestSchedule {
        @SerializedName("Term")
        @Expose
        private String term;

        @SerializedName("Schedule")
        @Expose
        private List<TestScheduleDetail> schedules;


        /* constructor */
        public TestSchedule(String term, List<TestScheduleDetail> schedules) {
            this.term = term;
            this.schedules = schedules;
        }


        public String getTerm() {
            return term;
        }

        public List<TestScheduleDetail> getSchedules() {
            return schedules;
        }

        public List<CalendarSchedule> toCalendarSchedules() {
            List<CalendarSchedule> result = new ArrayList<>();

            for (TestScheduleDetail tsd : this.schedules) {
                result.add(tsd.toCalendarTestSchedule());
            }

            return result;
        }


        /* children classes */
        public static class TestScheduleDetail {
            @SerializedName("Date")
            @Expose
            private String date;

            @SerializedName("Period")
            @Expose
            private String period;

            @SerializedName("Class")
            @Expose
            private String classId;

            @SerializedName("Room")
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

            @SerializedName("Group")
            @Expose
            private String group;

            @SerializedName("FromOrdinal")
            @Expose
            private String fromOrdinal;



            public TestScheduleDetail(String date, String period, String room, String classId,
                                      String testType, String subjectName, String notes,
                                      String group, String fromOrdinal) {
                this.date = date;
                this.period = period;
                this.room = room;
                this.classId = classId;
                this.testType = testType;
                this.subjectName = subjectName;
                this.notes = notes;
                this.group = group;
                this.fromOrdinal = fromOrdinal;
            }


            public CalendarTestSchedule toCalendarTestSchedule() {
                return new CalendarTestSchedule(date, subjectName, period, room,
                    notes, group, fromOrdinal, testType, classId);
            }


            public String getDate() {
                return date;
            }

            public String getPeriod() {
                return period;
            }

            public String getClassId() {
                return classId;
            }

            public String getRoom() {
                return room;
            }

            public String getTestType() {
                return testType;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public String getNotes() {
                return notes;
            }

            public String getGroup() {
                return group;
            }

            public String getFromOrdinal() {
                return fromOrdinal;
            }
        }
    }
    public static class NormalSchedule {
        @SerializedName("term")
        @Expose
        private String term;
        @SerializedName("schedule")
        @Expose
        private List<Schedule> schedule = null;


        public String getTerm() {
            return term;
        }

        public List<Schedule> getSchedule() {
            return schedule;
        }

        public List<CalendarSchedule> toCalendarSchedules() {
            List<CalendarSchedule> result = new ArrayList<>();

            /* loop through all days in a week (0 --> 6) */
            for (Schedule s : this.schedule) {
                if (s.morning.size() > 0) {
                    for (NormalScheduleDetail nsd : s.morning) {
                        result.add(nsd.toNormalCalendarSchedule());
                    }
                }

                if (s.afternoon.size() > 0) {
                    for (NormalScheduleDetail nsd : s.afternoon) {
                        result.add(nsd.toNormalCalendarSchedule());
                    }
                }

                if (s.evening.size() > 0) {
                    for (NormalScheduleDetail nsd : s.evening) {
                        result.add(nsd.toNormalCalendarSchedule());
                    }
                }
            }

            return result;
        }


        /* children classes */
        public static class Schedule {

            @SerializedName("dayIndex")
            @Expose
            private Integer dayIndex;
            @SerializedName("morning")
            @Expose
            private List<NormalScheduleDetail> morning = null;
            @SerializedName("afternoon")
            @Expose
            private List<NormalScheduleDetail> afternoon = null;
            @SerializedName("evening")
            @Expose
            private List<NormalScheduleDetail> evening = null;


            public Integer getDayIndex() {
                return dayIndex;
            }

            public List<NormalScheduleDetail> getMorning() {
                return morning;
            }

            public List<NormalScheduleDetail> getAfternoon() {
                return afternoon;
            }

            public List<NormalScheduleDetail> getEvening() {
                return evening;
            }
        }


        public static class NormalScheduleDetail {
            @SerializedName("class_id")
            @Expose
            private String classId;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("completed")
            @Expose
            private Boolean completed;
            @SerializedName("subject_name")
            @Expose
            private String subjectName;
            @SerializedName("period")
            @Expose
            private String period;
            @SerializedName("teacher")
            @Expose
            private String teacher;
            @SerializedName("room")
            @Expose
            private String room;
            @SerializedName("date")
            @Expose
            private String date;


            public CalendarNormalSchedule toNormalCalendarSchedule() {
                return new CalendarNormalSchedule(date, subjectName, period, room,
                    teacher, completed, classId, type);
            }


            public String getClassId() {
                return classId;
            }

            public String getType() {
                return type;
            }

            public Boolean getCompleted() {
                return completed;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public String getPeriod() {
                return period;
            }

            public String getTeacher() {
                return teacher;
            }

            public String getRoom() {
                return room;
            }

            public String getDate() {
                return date;
            }
        }

        public static class Afternoon extends NormalScheduleDetail {

        }

        public static class Morning extends NormalScheduleDetail {

        }

        public static class Evening extends NormalScheduleDetail {

        }
    }

    public static class CalendarSchedule {
        public enum ScheduleType { Test, Learning }

        private String date;
        private ScheduleType scheduleType;
        private String subjectName;
        private String period;
        private String room;

        public CalendarSchedule(String date, ScheduleType scheduleType, String subjectName, String period, String room) {
            this.date = date;
            this.scheduleType = scheduleType;
            this.subjectName = subjectName;
            this.period = period;
            this.room = room;
        }


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ScheduleType getScheduleType() {
            return scheduleType;
        }

        public void setScheduleType(ScheduleType scheduleType) {
            this.scheduleType = scheduleType;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
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
    }
    public static class CalendarNormalSchedule extends CalendarSchedule {
        private String teacher;
        private boolean completed;
        private String classId;
        private String learningType;

        public CalendarNormalSchedule(
                String date, String subjectName, String period, String room,
                String teacher, boolean completed, String classId, String learningType) {
            super(date, ScheduleType.Learning, subjectName, period, room);

            this.teacher = teacher;
            this.completed = completed;
            this.classId = classId;
            this.learningType = learningType;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getLearningType() {
            return learningType;
        }

        public void setLearningType(String learningType) {
            this.learningType = learningType;
        }
    }
    public static class CalendarTestSchedule extends CalendarSchedule {
        private String notes;
        private String group;
        private String fromOrdinal;
        private String testType;
        private String classId; /* e.g. "0101060015- DH18LT;DH18TL; DH18TM2; DH18TM3" */


        public CalendarTestSchedule(
                String date, String subjectName, String period, String room,
                String notes, String group, String fromOrdinal, String testType, String classId) {
            super(date, ScheduleType.Test, subjectName, period, room);

            this.notes = notes;
            this.group = group;
            this.fromOrdinal = fromOrdinal;
            this.testType = testType;
            this.classId = classId;
        }


        @Override
        public String getDate() {
            return super.getDate().split("\\(")[1].split("\\)")[0];
        }


        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getFromOrdinal() {
            return fromOrdinal;
        }

        public void setFromOrdinal(String fromOrdinal) {
            this.fromOrdinal = fromOrdinal;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }
    }


    public static class ReceiptInfo {
        private String content;
        private String totalCost;
        private String bankName;
        private String transactionTime;
        private String dateOfPayment;
        private String debitStatus;
        private String detailsLink;

        public String getContent() {
            return content;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public String getBankName() {
            return bankName;
        }

        public String getTransactionTime() {
            return transactionTime;
        }

        public String getDateOfPayment() {
            return dateOfPayment;
        }

        public String getDebitStatus() {
            return debitStatus;
        }

        public String getDetailsLink() {
            return detailsLink;
        }
    }

}
