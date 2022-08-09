package database;

import MODELS.StudentTemp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @OrderBy("id")
    private List<Professor> professors;
    @Column(name = "educational_assistant_id")
    private long assistantId;
    @OneToMany(mappedBy = "faculty")
    private Set<Course> courses;
    @OneToMany(mappedBy = "faculty")
    private Set<Classroom> classrooms;

    public Faculty() {
    }

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
        students = new HashSet<>();
        professors = new ArrayList<>();
        courses = new HashSet<>();
        classrooms = new HashSet<>();
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
    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
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
    public List<Professor> getProfessors() {
        return professors;
    }
    public long getAssistantId() {
        return assistantId;
    }
    public Set<Course> getCourses() {
        return courses;
    }
    public Set<Classroom> getClassrooms() {
        return classrooms;
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
    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
    public void setAssistantId(long assistantId) {
        this.assistantId = assistantId;
    }
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    public void setClassrooms(Set<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", professors=" + professors +
                ", assistantId=" + assistantId +
                ", courses=" + courses +
                ", classrooms=" + classrooms +
                '}';
    }

    public void setAssistant(EducationalAssistant educationalAssistant) {
        setAssistantId(educationalAssistant.getId());
    }

    public EducationalAssistant getAssistant() {
        Database.getInstance().closeSession();
        return (EducationalAssistant) Database.getInstance().getUser(getAssistantId());
    }
}