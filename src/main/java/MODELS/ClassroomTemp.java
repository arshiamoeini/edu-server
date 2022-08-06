package MODELS;

import LOGIC.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClassroomTemp {
    private int id;
    private int facultyID;
    private int courseID;
    private int teacherID;
    private int capacity;
    private ArrayList<LocalDateTime> time;
    private boolean registered;
    private boolean aFinal;

    public boolean isRegistered() {
        return registered;
    }
    public boolean setRegistered() {
        boolean canDoRegister = true;
        for (classRating rating: students) {
            if (rating.getScore() == -1) canDoRegister = false;
        }
        if (canDoRegister) {
            this.registered = true;
            return true;
        } else {
            System.out.println("some not scored student remain");
            return false;
        }
    }

    public ArrayList<classRating> getRatings() {
        return students;
    }

    public boolean isaFinal() {
        return aFinal;
    }

    public void setAFinal() {
        registered = false;
        aFinal = true;
        //TODO add student report
    }

    public void addStudent(long studentID) {
        classRating rating = new classRating(studentID);
        students.add(rating);
        rating.getStudent().addWeeklyClass(id);
        {
            rating.score = 12;
            setRegistered();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public class classRating {
        private int facultyID;
        private int classID;
        private long studentID;
        private double score = -1;
        private String protest;
        private String answer;

        public classRating(long studentID) {
            this.studentID = studentID;
            protest = "  ";
            answer = "   ";
        }

        public ClassroomTemp getClassroom() {
            return University.getInstance().getFaculty(facultyID).getClassroom(classID);
        }

        public double getScore() {
            return score;
        }

        public String getProtest() {
            return protest;
        }
        public void setProtest(String protest) {
            this.protest = protest;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public long getStudentID() {
            return studentID;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getStudentName() {
            return getStudent().getName();
        }

        public StudentTemp getStudent() {
            return (StudentTemp) University.getInstance().getUser(studentID);
        }
    }
    private ArrayList<classRating> students;
    public classRating getRatingWithID(long id) {
        for (classRating rating: students) {
            if (id == rating.getStudentID()) {
                return rating;
            }
        }
        return null;
    }
    private LocalDateTime examDate;

    public ClassroomTemp(int courseID, int teacherID, int capacity, LocalDateTime examDate, ArrayList<LocalDateTime> time) {
        this.courseID = courseID;
        this.teacherID = teacherID;
        this.capacity = capacity;
        this.examDate = examDate;
        this.time = time;

        students = new ArrayList<>();
    }

    public void edit(int capacity, String professorName, LocalDateTime dateTime) {
        this.examDate = dateTime;
        this.capacity = Math.min(capacity, students.size());

        Object[] professorsName = getFaculty().getProfessorsName();
        for (int i = 0;i <  professorsName.length;++i) {
            if (((String) professorsName[i]).equals(professorName)) {
                teacherID = i;
                return;
            }
        }
        Logger.log("WARN    : can,t find teacher name");
    }
    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }
    public FacultyTemp getFaculty() { return University.getInstance().getFaculty(facultyID); }

    public CourseTemp getCourse() { return getFaculty().getCourse(courseID); }

    public int getCapacity() { return capacity; }

    public int getRegistrationNumber() {
        return students.size();
    }

    public ProfessorTemp getProfessor() {
        return getFaculty().getProfessor(teacherID);
    }
    public String getProfessorName() {
        return getProfessor().getName();
    }
    public LocalDateTime getExamDate() {
        return examDate;
    }
    public ArrayList<LocalDateTime> getTime() {
        return time;
    }
}
