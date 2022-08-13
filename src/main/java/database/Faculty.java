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
    @Column(name = "name", nullable = false)
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
    @Column
    private boolean timeToTakingClasses = false;

    public Faculty() {
    }

    public Faculty(String name) {
        this.name = name;
        students = new HashSet<>();
        professors = new ArrayList<>();
        courses = new HashSet<>();
        classrooms = new HashSet<>();
    }


    public void addStudent(Student student) {
        getStudents().add(student);
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
    public boolean isTimeToTakingClasses() {
        return timeToTakingClasses;
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
    public void setTimeToTakingClasses(boolean timeToTakingClasses) {
        this.timeToTakingClasses = timeToTakingClasses;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", students=" + students +
                ", professors=" + professors +
                ", assistantId=" + assistantId +
                ", courses=" + courses +
                ", classrooms=" + classrooms +
                ", timeToTakingClasses=" + timeToTakingClasses +
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