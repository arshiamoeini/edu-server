package database;

import MODELS.ClassroomTemp;
import MODELS.FacultyTemp;
import MODELS.University;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UserTemp {
    private long id;
    private int hashOfPassword;
    private int facultyID;

    String name = "somebody";
    long nationalCode;
    long phoneNumber;
    String email;
//    Image image;

    protected ArrayList<Integer> weeklyClassesID;

    public UserTemp() {
    }
    public UserTemp(long id, String password) {
        this.id = id;
        setPassword(password);

        weeklyClassesID = new ArrayList<>();
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }
    public void setPassword(String password) { hashOfPassword = password.hashCode(); }

    public void addClass(int Id) {
        weeklyClassesID.add(Id);
    }
    public FacultyTemp getFaculty() { return University.getInstance().getFaculty(facultyID); }

    public boolean isIn(FacultyTemp faculty) {
        return facultyID == faculty.getID();
    }

    public List<ClassroomTemp> getWeeklyClasses() {
        FacultyTemp faculty = getFaculty();
        return weeklyClassesID.stream().map(x -> faculty.getClassroom(x)).collect(Collectors.toList());
    }


    public void addWeeklyClass(int classroomID) {
        weeklyClassesID.add(classroomID);
    }

    public long getId() {
        return id;
    }
    public int getHashOfPassword() {
        return hashOfPassword;
    }
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setHashOfPassword(int hashOfPassword) {
        this.hashOfPassword = hashOfPassword;
    }
    public void setName(String name) {
        this.name = name;
    }
}
