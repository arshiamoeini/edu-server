package database;

import MODELS.StudentTemp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faculty")
public class Faculty implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    private String name;
    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;
    @OneToMany(mappedBy = "faculty")
    private Set<Professor> professors;
    @OneToMany(mappedBy = "faculty")
    private Set<Course> courses;

    public Faculty() {
    }

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
        students = new HashSet<>();
        professors = new HashSet<>();
        courses = new HashSet<>();
    }


    public void addStudent(Student student) {
        students.add(student);
    }
    public void addProfessor(Professor professor) {
        professors.add(professor);
    }
    public void addCourse(Course course) {
        courses.add(course);
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<Student> getStudents() {
        return students;
    }
    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }


    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", professors=" + professors +
                ", courses=" + courses +
                '}';
    }
}