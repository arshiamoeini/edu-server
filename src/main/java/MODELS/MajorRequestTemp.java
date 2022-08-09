package MODELS;

public class MajorRequestTemp {
    private int WithFacultyID;
    private boolean rejected = false;
    private int accepted = 0;

    public MajorRequestTemp(int withFacultyID) {
        this.WithFacultyID = withFacultyID;
    }

    public String getFacultyName() {
        return University.getInstance().getFaculty(WithFacultyID).getMassage();
    }

    public String getStatus() {
        if (rejected) return "rejected";
        if (accepted == 2) return "accepted";
        return "registered";
    }
}
