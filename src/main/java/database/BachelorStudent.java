package database;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bachelor_student")
public class BachelorStudent extends Student implements Serializable {
    @OneToMany
    @OrderBy("id")
    List<MajorRequest> majorRequests;

    public BachelorStudent() {
    }

    public BachelorStudent(long id, String password, Faculty faculty) {
        super(id, password, faculty);
        majorRequests = new ArrayList<>();
    }

    public List<MajorRequest> getMajorRequests() {
        return majorRequests;
    }

    public void setMajorRequests(List<MajorRequest> majorRequests) {
        this.majorRequests = majorRequests;
    }

    public void addMajorRequest(MajorRequest majorRequest) {
        getMajorRequests().add(majorRequest);
    }
}