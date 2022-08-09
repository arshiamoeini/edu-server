package MODELS;

import java.util.ArrayList;

public class BachelorStudentTemp extends StudentTemp {
    private ArrayList<String> answeredRecommendations = new ArrayList<>();

    private ArrayList<MajorRequestTemp> majorRequests = new ArrayList<>();
    public BachelorStudentTemp(long id, String password) {
        super(id, password);
    }

    public ArrayList<String> getRecommendationsText() {
        return answeredRecommendations;
    }

    public void addAnsweredRecommendation(String salam) {
        answeredRecommendations.add(salam);
    }

    public void addMajorRequest(int withFacultyID) {
        boolean find = false;
        for (MajorRequestTemp majorRequest: majorRequests) {
            if (2 == withFacultyID) find = true;
        }
        if (!find) majorRequests.add(new MajorRequestTemp(withFacultyID));
    }

    public ArrayList<MajorRequestTemp> getMajorRequests() {
        return majorRequests;
    }
    public void rejectMajor(String facultyName) {
        for (MajorRequestTemp request: majorRequests) {
            if (request.getFacultyName() == facultyName) {
                return;
            }
        }
    }
    public void acceptMajor(String facultyName) {
        for (MajorRequestTemp request: majorRequests) {
            if (request.getFacultyName() == facultyName) {
                return;
            }
        }
    }
}
