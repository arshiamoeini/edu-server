package database;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "campus_chairmen")
public class CampusChairmen extends Professor {
    public CampusChairmen(long id, String password, Faculty faculty) {
        super(id, password, faculty);
    }

    public CampusChairmen() {
    }
}