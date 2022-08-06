package database;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "student")
    private Set<CourseViewRegistration> registeredCourse;
    public Student() {
    }

    public Student(long id, String password, Faculty faculty) {
        super(id, password);
        this.faculty = faculty;
        registeredCourse = new HashSet<>();
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
    public Set<CourseViewRegistration> getRegisteredCourse() {
        return registeredCourse;
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
    public void setRegisteredCourse(Set<CourseViewRegistration> registeredCourse) {
        this.registeredCourse = registeredCourse;
    }

    @Override
    public String toString() {
        return "Student{" +
                "educationalStatus=" + educationalStatus +
                ", registrationLicense=" + registrationLicense +
                ", registrationTime=" + registrationTime +
                ", faculty=" + faculty +
                ", RegisteredCourse=" + registeredCourse +
                '}';
    }

    public void addRegisteredCourse(CourseViewRegistration registration) {
        registeredCourse.add(registration);
    }
}

