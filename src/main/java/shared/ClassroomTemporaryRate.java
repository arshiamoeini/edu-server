package shared;

import database.ClassroomRating;
import database.CourseViewRegistration;

public class ClassroomTemporaryRate {
    private Long id;
    private String courseName;
    private String teacherName;
    private double recordedScore;
    private String registerAProtest;
    private String seeAnswer;


    public ClassroomTemporaryRate(ClassroomRating rating) {
        id = rating.getId();
        courseName = rating.getClassroom().getName();
        teacherName = rating.getClassroom().getTeacher().getName();
        recordedScore = rating.getScore();
        registerAProtest = rating.getProtest();
        seeAnswer = rating.getAnswer();
    }

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public double getRecordedScore() {
        return recordedScore;
    }

    public String getRegisterAProtest() {
        return registerAProtest;
    }

    public String getSeeAnswer() {
        return seeAnswer;
    }
}
