package database;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_view")
public class CourseView implements Serializable {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
    @OneToMany(mappedBy = "courseView")
    private Set<CourseViewRegistration> studentRegistrations;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Professor teacher;

    public CourseView() {
    }

    public CourseView(Classroom classroom) {
        this.classroom = classroom;
        name = classroom.getName();
        teacher = classroom.getTeacher();
        studentRegistrations = new HashSet<>();
    }

    public Classroom getClassroom() {
        return classroom;
    }
    public Set<CourseViewRegistration> getStudentRegistrations() {
        return studentRegistrations;
    }
    public String getName() {
        return name;
    }
    public Professor getTeacher() {
        return teacher;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    public void setStudentRegistrations(Set<CourseViewRegistration> studentRegistrations) {
        this.studentRegistrations = studentRegistrations;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTeacher(Professor teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "CourseView{" +
                "classroom=" + classroom +
                ", studentRegistrations=" + studentRegistrations +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }

    public LocalDateTime getExamDate() {
        return getClassroom().getExamDate();
    }

    public CourseViewRegistration addStudent(Student student, boolean ta) {
        CourseViewRegistration registration = new CourseViewRegistration(this, student, ta);
        addRegisteredStudent(registration);
        student.addRegisteredCourse(registration);
        return registration;
    }

    private void addRegisteredStudent(CourseViewRegistration registration) {
        studentRegistrations.add(registration);
    }
}