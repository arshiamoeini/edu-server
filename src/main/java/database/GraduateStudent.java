package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "graduate_student")
public class GraduateStudent extends Student implements Serializable {
    public GraduateStudent() {
    }

    public GraduateStudent(long id, String password, Faculty faculty) {
        super(id, password, faculty);
    }


}