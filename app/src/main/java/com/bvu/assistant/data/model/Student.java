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

    public static class TestSchedule {
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

        public List<TestScheduleDetail> getSchedules() {
            return schedules;
        }


        public static class TestScheduleDetail {
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

            public String getPeriod() {
                return period;
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
        }
    }

}
