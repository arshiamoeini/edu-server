package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "bachelor_student")
public class BachelorStudent extends Student implements Serializable {

    public BachelorStudent() {
    }

    public BachelorStudent(long id, String password, Faculty faculty) {
        super(id, password, faculty);
    }

}