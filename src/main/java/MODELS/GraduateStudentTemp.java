package MODELS;

import java.util.ArrayList;

public class GraduateStudentTemp extends StudentTemp {
    private ArrayList<String> answeredRecommendations = new ArrayList<>();

    public GraduateStudentTemp(long id, String password) {
        super(id, password);
    }

    public ArrayList<String> getRecommendationsText() {
        return answeredRecommendations;
    }

    public void addAnsweredRecommendation(String salam) {
        answeredRecommendations.add(salam);
    }
}
