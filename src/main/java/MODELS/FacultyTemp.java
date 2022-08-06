package MODELS;

import database.UserTemp;

import java.util.ArrayList;

public class FacultyTemp implements ContainMessage {
    private String name;
    private int facultyID;
    private ArrayList<ProfessorTemp> professors;
    private ArrayList<StudentTemp> students;


    private ArrayList<CourseTemp> courses;
    private ArrayList<ClassroomTemp> classrooms;

    private int campusChairmenID;
    private int educationalAssistantID;

    public void addEducationalRequest(RequestType type, String text, long studentSourceID) {
        educationalRequests.add(new EducationalRequest(type, text, studentSourceID));
    }
    public void removeRequest(EducationalRequest request) {
        educationalRequests.remove(request);
    }

    public ArrayList<EducationalRequest> getEducationalRequests() {
        return educationalRequests;
    }

    public CourseTemp getCourseWithLongID(int LongID) {
        for (CourseTemp course: courses) {
            if (course.getId() == LongID) {
                return course;
            }
        }
        return null;
    }

    public int lastAddedCourseID() {
        return courses.size() - 1;
    }

    public enum RequestType implements ContainMessage {
        WITHDRAWAL("Withdrawal"),
        MAJOR("Major");
        private String message;

        RequestType(String message) {
            this.message = message;
        }

        @Override
        public String getMassage() {
            return message;
        }
    }
    public class EducationalRequest implements ContainMessage {
        private RequestType requestType;
        private String message;
        private long studentSourceID;

        public EducationalRequest(RequestType requestType, String message, long studentSourceID) {
            this.requestType = requestType;
            this.message = message;
            this.studentSourceID = studentSourceID;
        }
        public RequestType getRequestType() {
            return requestType;
        }
        public StudentTemp getStudent() {
            return (StudentTemp) University.getInstance().getUser(studentSourceID);
        }

        @Override
        public String getMassage() {
            return message;
        }
    }
    private ArrayList<EducationalRequest> educationalRequests;
    public FacultyTemp(String name, int facultyID) {
        this.name = name;
        this.facultyID = facultyID;
        this.professors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
        this.campusChairmenID = 0;
        this.educationalAssistantID = 1;
        this.educationalRequests = new ArrayList<>();
    }

    /*
    public Faculty() {
        this.facultyTag = facultyTag;
        this.campusChairmen = campusChairmen;

        courses = new ArrayList<>();
        classrooms = new ArrayList<>();
        professors = new ArrayList<>();
        students = new ArrayList<>();
    }
    */

    @Override
    public String getMassage() { return name; }

    public void addCourses(String name, int id, ArrayList<CourseTemp> prerequisite, ArrayList<CourseTemp> coRequisite, CourseTemp.Program program) {
        courses.add(new CourseTemp(facultyID, name, id, prerequisite, coRequisite, program));
    }
    public void addClassrooms(ClassroomTemp classroom) {
        classrooms.add(classroom);
        classroom.setFacultyID(facultyID);
        classroom.setId(classrooms.size() - 1);
        classroom.getProfessor().addWeeklyClass(classrooms.size() - 1);
    }

    public void addStudent(StudentTemp student) {
        students.add(student);
        student.setFacultyID(facultyID);
    }
    public void addProfessor(ProfessorTemp professor) {
        professors.add(professor);
        professor.setFacultyID(facultyID);
    }

    public void setCampusChairmen(CampusChairmenTemp campusChairmen) {
    }
    public void setEducationalAssistant(EducationalAssistantTemp educationalAssistant) {
    }
    public void deleteUsers() {
        professors.clear();
        students.clear();
    }
    public ArrayList<CourseTemp> getCourses() { return courses; }
    public ArrayList<ClassroomTemp> getClassrooms() { return classrooms; }

    public UserTemp getUser(long userID) {
        for (StudentTemp student: students) {
            if (student.getId() == userID) {
                return student;
            }
        }
        for (ProfessorTemp professor: professors){
            if (professor.getId() == userID) {
                return professor;
            }
        }
        return null;
    }

    public ProfessorTemp getProfessor(int id) {
        return professors.get(id);
    }
    public CourseTemp getCourse(int id) { return courses.get(id); }
    public int getID() { return facultyID; }

    public int getEducationalAssistantID() {
        if (educationalAssistantID == -1) {
            return campusChairmenID;
        } else {
            return educationalAssistantID;
        }
    }

    public ClassroomTemp getClassroom(int index) {
        return classrooms.get(index);
    }

    public Object[] getProfessorsName() {
        return professors.stream().map(ProfessorTemp::getName).toArray();
    }
}
