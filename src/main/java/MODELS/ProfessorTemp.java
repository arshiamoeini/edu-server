package MODELS;

import database.UserTemp;
import shared.MasterLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorTemp extends UserTemp {

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
