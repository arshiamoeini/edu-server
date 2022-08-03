package database;

import MODELS.CourseTemp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @Column
    private String name;
    @Column
    private int credit;
    @ManyToOne
    @JoinTable(name="prerequisite",
            joinColumns={@JoinColumn(name="EMPLOYEE_ID")},
            inverseJoinColumns={@JoinColumn(name="COLLEAGUE_ID")})
    private Set<Course> prerequisite;
    @ManyToOne
    private Set<Course> coRequisite;

    public Course() {
    }



    public Faculty getFaculty() {
        return faculty;
    }
    public Integer getId() {
        return id;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    public void setId(Integer id) {
        this.id = id;
    }

}