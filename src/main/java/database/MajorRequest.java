package database;

import javax.persistence.*;

@Entity
@Table(name = "major_request")
public class MajorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "target_faculty_id", nullable = false)
    private Faculty targetFaculty;
    @Column
    private boolean rejected = false;
    @Column
    private int accepted = 0;

    public MajorRequest() {
    }

    public MajorRequest(Faculty targetFaculty) {
        this.targetFaculty = targetFaculty;
    }

    public Integer getId() {
        return id;
    }
    public Faculty getTargetFaculty() {
        return targetFaculty;
    }
    public boolean isRejected() {
        return rejected;
    }
    public int getAccepted() {
        return accepted;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setTargetFaculty(Faculty targetFaculty) {
        this.targetFaculty = targetFaculty;
    }
    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getStatus() {
        if (rejected) return "rejected";
        if (accepted == 2) return "accepted";
        return "registered";
    }
}