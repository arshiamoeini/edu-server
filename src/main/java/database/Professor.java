package database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Professor extends User implements Serializable {
  /*  public enum MasterLevel implements ContainMessage {
        ASSISTANT_PROFESSOR("Assistant Professor"),
        ASSOCIATEـPROFESSOR("Associate Professor"),
        FULL_PROFESSOR("full Professor");
        public String message;
        MasterLevel(String message) {
            this.message = message;
        }
        @Override
        public String getMassage() { return message; }
    }*/
    @Column
    private long roomNumber;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @OneToMany(mappedBy = "teacher")
    private Set<Classroom> classrooms;
    @OneToMany(mappedBy = "teacher")
    private Set<CourseView> courseViews;

  public Professor() {
    }

  public Professor(long id, String password, Faculty faculty) {
    super(id, password);
    this.faculty = faculty;
    classrooms = new HashSet<>();
    courseViews = new HashSet<>();
  }

  public void addClassroom(Classroom classroom) {
      classrooms.add(classroom);
  }

  public long getRoomNumber() {
      return roomNumber;
    }
  public Faculty getFaculty() {
    return faculty;
  }
  public Set<Classroom> getClassrooms() {
    return classrooms;
  }
  public Set<CourseView> getCourseViews() {
    return courseViews;
  }

  public void setRoomNumber(long roomNumber) {
      this.roomNumber = roomNumber;
    }
  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }
  public void setClassrooms(Set<Classroom> classrooms) {
    this.classrooms = classrooms;
  }
  public void setCourseViews(Set<CourseView> courseViews) {
    this.courseViews = courseViews;
  }

  @Override
  public String toString() {
    return "Professor{" +
            "roomNumber=" + roomNumber +
            ", faculty=" + faculty +
            ", classrooms=" + classrooms +
            '}';
  }

  public void addCourseView(CourseView courseView) {
    courseViews.add(courseView);
  }
}