package database;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Student extends User implements Serializable {
    // @Override
    // public  Class<? extends User> getType() {
    //    return Student.class;
    // }

    @Column
    public EducationalStatus educationalStatus;
   // private Professor Supervisor;
    @Column
    private boolean registrationLicense;
    @Column
    private LocalDateTime registrationTime;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    public Student() {
    }

    public Student(long id, String password, Faculty faculty) {
        super(id, password);
        this.faculty = faculty;
    }

    /* public void setSupervisor(Professor supervisor) {
        Supervisor = supervisor;
    }*/
  /*  public Professor getSupervisor() {
        return Supervisor;
    }*/
    /*public ArrayList<Classroom.classRating> getRegisteredRecords() {
        ArrayList<Classroom.classRating> ans = new ArrayList<>();
        Faculty faculty = getFaculty();
        for (int classroomID: weeklyClassesID) {
            Classroom classroom = faculty.getClassroom(classroomID);
            if (classroom.isRegistered()) {
                ans.add(classroom.getRatingWithID(getId()));
            }
        }
        return ans;
    }*/

    public EducationalStatus getEducationalStatus() {
        return educationalStatus;
    }
    public boolean isRegistrationLicense() {
        return registrationLicense;
    }
    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }
    public Faculty getFaculty() {
        return faculty;
    }

    public void setEducationalStatus(EducationalStatus educationalStatus) {
        this.educationalStatus = educationalStatus;
    }
    public void setRegistrationLicense(boolean registrationLicense) {
        this.registrationLicense = registrationLicense;
    }
    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Student{" +
                "educationalStatus=" + educationalStatus +
                ", registrationLicense=" + registrationLicense +
                ", registrationTime=" + registrationTime +
                ", faculty=" + faculty +
                '}';
    }
}

