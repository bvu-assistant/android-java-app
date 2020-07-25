package com.bvu.assistant.model.Student;

import java.util.ArrayList;

public class HoSo {
    private String name;
    private LearningStatus learningStatus;
    private PersonalProfile personalProfile;
    private FamilyProfile familyProfile;

    public HoSo(String name, LearningStatus learningStatus, PersonalProfile personalProfile, FamilyProfile familyProfile) {
        this.name = name;
        this.learningStatus = learningStatus;
        this.personalProfile = personalProfile;
        this.familyProfile = familyProfile;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LearningStatus getLearningStatus() {
        return learningStatus;
    }

    public void setLearningStatus(LearningStatus learningStatus) {
        this.learningStatus = learningStatus;
    }

    public PersonalProfile getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(PersonalProfile personalProfile) {
        this.personalProfile = personalProfile;
    }

    public FamilyProfile getFamilyProfile() {
        return familyProfile;
    }

    public void setFamilyProfile(FamilyProfile familyProfile) {
        this.familyProfile = familyProfile;
    }



    public class LearningStatus {
        private String status;
        private String session;
        private String gender;
        private String name;

        private String educatingRank;
        private String educatingType;
        private String major;
        private String mainMajor;
        private String department;
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

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEducatingRank() {
            return educatingRank;
        }

        public void setEducatingRank(String educatingRank) {
            this.educatingRank = educatingRank;
        }

        public String getEducatingType() {
            return educatingType;
        }

        public void setEducatingType(String educatingType) {
            this.educatingType = educatingType;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getMainMajor() {
            return mainMajor;
        }

        public void setMainMajor(String mainMajor) {
            this.mainMajor = mainMajor;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getYouthActivity() {
            return youthActivity;
        }

        public void setYouthActivity(String youthActivity) {
            this.youthActivity = youthActivity;
        }
    }

    public class PersonalProfile {
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

        public Birth getBirth() {
            return birth;
        }

        public void setBirth(Birth birth) {
            this.birth = birth;
        }

        public PID getPid() {
            return pid;
        }

        public void setPid(PID pid) {
            this.pid = pid;
        }

        public Prioritize getPrioritize() {
            return prioritize;
        }

        public void setPrioritize(Prioritize prioritize) {
            this.prioritize = prioritize;
        }

        public Nationality getNationality() {
            return nationality;
        }

        public void setNationality(Nationality nationality) {
            this.nationality = nationality;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public BVU365 getBvu365() {
            return bvu365;
        }

        public void setBvu365(BVU365 bvu365) {
            this.bvu365 = bvu365;
        }

        public Gang getGang() {
            return gang;
        }

        public void setGang(Gang gang) {
            this.gang = gang;
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
            private Address address;
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

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
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

    public class FamilyProfile {
        private ArrayList<Relative> relativeList;

        public FamilyProfile(ArrayList<Relative> relativeList) {
            this.relativeList = relativeList;
        }

        public ArrayList<Relative> getRelativeList() {
            return relativeList;
        }

        public void setRelativeList(ArrayList<Relative> relativeList) {
            this.relativeList = relativeList;
        }

        public class Relative {
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

            public void setName(String name) {
                this.name = name;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
            }

            public String getNationality() {
                return nationality;
            }

            public void setNationality(String nationality) {
                this.nationality = nationality;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
