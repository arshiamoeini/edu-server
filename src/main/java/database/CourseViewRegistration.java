package database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "course_view_registration")
public class CourseViewRegistration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CourseViewRegistration_SEQ")
    @SequenceGenerator(name = "CourseViewRegistration_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseView courseView;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @Column
    private boolean ta;

    public CourseViewRegistration() {
    }

    public CourseViewRegistration(CourseView courseView, Student student, boolean ta) {
        this.courseView = courseView;
        this.student = student;
        this.ta = ta;
    }

    public Long getId() {
        return id;
    }
    public CourseView getCourseView() {
        return courseView;
    }
    public Student getStudent() {
        return student;
    }
    public boolean isTa() {
        return ta;
    }

    public void setCourseView(CourseView courseView) {
        this.courseView = courseView;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setTa(boolean ta) {
        this.ta = ta;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CourseViewRegistration{" +
                "id=" + id +
                ", courseView=" + courseView +
                ", student=" + student +
                ", ta=" + ta +
                '}';
    }
}