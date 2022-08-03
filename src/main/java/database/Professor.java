package database;

import MODELS.ContainMessage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

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

    public Professor() {
    }

  public Professor(long id, String password, Faculty faculty) {
    super(id, password);
    this.faculty = faculty;
  }

  public long getRoomNumber() {
      return roomNumber;
    }
  public Faculty getFaculty() {
    return faculty;
  }

    public void setRoomNumber(long roomNumber) {
      this.roomNumber = roomNumber;
    }
  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }

  @Override
    public String toString() {
      return "Professor{" +
              "roomNumber=" + roomNumber +
              '}';
    }
}