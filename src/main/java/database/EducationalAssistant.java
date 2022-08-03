package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "educational_assistant")
public class EducationalAssistant extends Professor implements Serializable {
    public EducationalAssistant(long id, String password, Faculty faculty) {
        super(id, password, faculty);
    }

    public EducationalAssistant() {
    }
}