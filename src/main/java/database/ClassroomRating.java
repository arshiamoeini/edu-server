package database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classroom_rating")
public class ClassroomRating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ClassroomRating_SEQ")
    @SequenceGenerator(name = "ClassroomRating_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public ClassroomRating() {
    }

    public ClassroomRating(Classroom classroom, Student student) {
        this.classroom = classroom;
        this.student = student;
    }

    public Long getId() {
        return id;
    }
    public Classroom getClassroom() {
        return classroom;
    }
    public Student getStudent() {
        return student;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "ClassroomRating{" +
                "id=" + id +
                ", classroom=" + classroom +
                ", student=" + student +
                '}';
    }
}