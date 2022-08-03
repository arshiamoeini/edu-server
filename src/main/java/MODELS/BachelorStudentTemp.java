package MODELS;

import java.util.ArrayList;

public class BachelorStudentTemp extends StudentTemp {
    private ArrayList<String> answeredRecommendations = new ArrayList<>();

    public class MajorRequest {
        private int WithFacultyID;
        private boolean rejected = false;
        private int accepted = 0;

        public MajorRequest(int withFacultyID) {
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
    private ArrayList<MajorRequest> majorRequests = new ArrayList<>();
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
        for (MajorRequest majorRequest: majorRequests) {
            if (majorRequest.WithFacultyID == withFacultyID) find = true;
        }
        if (!find) majorRequests.add(new MajorRequest(withFacultyID));
    }

    public ArrayList<MajorRequest> getMajorRequests() {
        return majorRequests;
    }
    public void rejectMajor(String facultyName) {
        for (MajorRequest request: majorRequests) {
            if (request.getFacultyName() == facultyName) {
                request.rejected = true;
                return;
            }
        }
    }
    public void acceptMajor(String facultyName) {
        for (MajorRequest request: majorRequests) {
            if (request.getFacultyName() == facultyName) {
                ++request.accepted;
                return;
            }
        }
    }
}
