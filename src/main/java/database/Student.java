package database;

import shared.Program;

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

    @Enumerated(EnumType.STRING)
    public EducationalStatus educationalStatus = EducationalStatus.ALLOWED_TO_REGISTER;
   // private Professor Supervisor;
    @Column
    private boolean registrationLicense;
    @Column
    private LocalDateTime registrationTime;
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Professor Supervisor;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @OneToMany(mappedBy = "student")
    private Set<ClassroomRating> classroomRates;
    @OneToMany(mappedBy = "student")
    private Set<CourseViewRegistration> registeredCourse;
    @Enumerated(EnumType.STRING)
    private Program program = Program.UNDERGRADUATE;
    public Student() {
    }

    public Student(long id, String password, Faculty faculty) {
        super(id, password);
        this.faculty = faculty;
        registeredCourse = new HashSet<>();
        classroomRates = new HashSet<>();
    }

    public Student(Faculty faculty, String[] data) {
        this(Long.parseLong(data[0]), data[1], faculty);
        setName(data[2]);
        setEmail(data[3]);
        setProgram(Program.values()[Integer.parseInt(data[4])]);
        setNationalCode(Long.parseLong(data[5]));
        setPhoneNumber(Long.parseLong(data[6]));
        setSupervisor(Database.getInstance().getProfessor(Long.parseLong(data[7])));
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
    public Set<ClassroomRating> getClassroomRates() {
        return classroomRates;
    }
    public Program getProgram() {
        return program;
    }
    public Professor getSupervisor() {
        return Supervisor;
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
    public void setClassroomRates(Set<ClassroomRating> classroomRates) {
        this.classroomRates = classroomRates;
    }
    public void setProgram(Program program) {
        this.program = program;
    }
    public void setSupervisor(Professor supervisor) {
        Supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "Student{" +
                "educationalStatus=" + educationalStatus +
                ", registrationLicense=" + registrationLicense +
                ", registrationTime=" + registrationTime +
                ", Supervisor=" + Supervisor +
                ", faculty=" + faculty +
                ", classroomRates=" + classroomRates +
                ", registeredCourse=" + registeredCourse +
                ", program=" + program +
                '}';
    }

    public void addRegisteredCourse(CourseViewRegistration registration) {
        getRegisteredCourse().add(registration);
    }

    public void addClassroom(ClassroomRating classroomRating) {
        getClassroomRates().add(classroomRating);
    }
}

