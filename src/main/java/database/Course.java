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
    @ManyToMany
    @JoinTable(name="prerequisite",
            joinColumns={@JoinColumn(name="course_id")},
            inverseJoinColumns={@JoinColumn(name="pre_course")})
    private Set<Course> prerequisite;
    @ManyToMany
    @JoinTable(name="co_requisite",
            joinColumns={@JoinColumn(name="course_id")},
            inverseJoinColumns={@JoinColumn(name="co_course")})
    private Set<Course> coRequisite;

    public Course() {
    }


    public Course(Integer id, Faculty faculty, String name, int credit, Set<Course> prerequisite, Set<Course> coRequisite) {
        this.id = id;
        this.faculty = faculty;
        this.name = name;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.coRequisite = coRequisite;
    }

    public Faculty getFaculty() {
        return faculty;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCredit() {
        return credit;
    }
    public Set<Course> getPrerequisite() {
        return prerequisite;
    }
    public Set<Course> getCoRequisite() {
        return coRequisite;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
    public void setPrerequisite(Set<Course> prerequisite) {
        this.prerequisite = prerequisite;
    }
    public void setCoRequisite(Set<Course> coRequisite) {
        this.coRequisite = coRequisite;
    }
}