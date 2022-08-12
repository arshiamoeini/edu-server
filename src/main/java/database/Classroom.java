package database;

import org.hibernate.annotations.Fetch;
import shared.Program;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @Enumerated(EnumType.STRING)
    private Program program;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = true)
    private Professor teacher;
    @Column
    private int credit;
    @Column
    private int capacity;
    @Column
    private int registrationNumber;
    @OneToMany(mappedBy = "classroom")
    private Set<ClassroomRating> studentsRating;
    @Column
    private LocalDateTime examDate;

    public Classroom() {
    }

    public Classroom(Long id, Faculty faculty, Course course, Professor teacher, Program program) {
        this.id = id;
        this.faculty = faculty;
        this.course = course;
        this.teacher = teacher;
        this.program = program;
        studentsRating = new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public Faculty getFaculty() {
        return faculty;
    }
    public Course getCourse() {
        return course;
    }
    public int getCapacity() {
        return capacity;
    }
    public Professor getTeacher() {
        return teacher;
    }
    public LocalDateTime getExamDate() {
        return examDate;
    }
    public Program getProgram() {
        return program;
    }
    public int getCredit() {
        return credit;
    }
    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public Set<ClassroomRating> getStudentsRating() {
        return studentsRating;
    }

    public void setTeacher(Professor teacher) {
        this.teacher = teacher;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }
    public void setProgram(Program program) {
        this.program = program;
    }
    public void setStudentsRating(Set<ClassroomRating> studentsRating) {
        this.studentsRating = studentsRating;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", faculty=" + faculty +
                ", program=" + program +
                ", course=" + course +
                ", teacher=" + teacher +
                ", credit=" + credit +
                ", capacity=" + capacity +
                ", studentsRating=" + studentsRating +
                ", examDate=" + examDate +
                '}';
    }

    public String getName() {
        return getCourse() == null ? "" : getCourse().getName();
    }
    public void addStudentRating(ClassroomRating studentRating) {
        studentsRating.add(studentRating);
    }
    public ClassroomRating addStudent(Student student) {
        ClassroomRating rating = new ClassroomRating(this, student);
        addStudentRating(rating);
        student.addClassroom(rating);
        return rating;
    }

    public int getCourseId() {
        return getCourse() == null ? 0 : getCourse().getId();
    }

    public long getTeacherId() {
        return getTeacher() == null ? 0 : getTeacher().getId();
    }
}