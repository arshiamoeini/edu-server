package MODELS;

import database.UserTemp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorTemp extends UserTemp {

    public enum MasterLevel implements ContainMessage {
        ASSISTANT_PROFESSOR("Assistant Professor"),
        ASSOCIATEـPROFESSOR("Associate Professor"),
        FULL_PROFESSOR("full Professor");
        public String message;
        MasterLevel(String message) {
            this.message = message;
        }
        @Override
        public String getMassage() { return message; }
    }

    private MasterLevel level;
    private long roomNumber;

    private ArrayList<Long> recommendations;
    public ProfessorTemp(long id, String password) {
        super(id, password);
        recommendations = new ArrayList<>();
    }

    public void addRecommendation(long id) {
        recommendations.add(id);
    }

    public List<StudentTemp> getRecommendedStudents() {
        return recommendations.stream().map(x -> (StudentTemp) University.getInstance().getUser(x)).collect(Collectors.toList());
    }

    public void removeRecommendation(long id) {
        recommendations.remove(id);
    }
}
