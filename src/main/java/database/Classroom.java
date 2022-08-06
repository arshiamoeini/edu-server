package database;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Professor teacher;
    @Column
    private int capacity;
    @Column
    private LocalDateTime examDate;

    public Classroom() {
    }

    public Classroom(Long id, Faculty faculty, Course course, Professor teacher) {
        this.id = id;
        this.faculty = faculty;
        this.course = course;
        this.teacher = teacher;
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

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", faculty=" + faculty +
                ", course=" + course +
                ", teacher=" + teacher +
                ", capacity=" + capacity +
                '}';
    }

    public String getName() {
        return getCourse().getName();
    }
}