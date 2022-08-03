package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ph_d_student")
public class PhDStudent extends Student implements Serializable {
    public PhDStudent() {
    }

    public PhDStudent(long id, String password, Faculty faculty) {
        super(id, password, faculty);
    }
}