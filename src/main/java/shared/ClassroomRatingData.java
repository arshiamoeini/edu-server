package shared;

import database.ClassroomRating;

public class ClassroomRatingData {
    private Long id;
    private String studentName;
    private long studentId;
    private double score;
    private String protest;
    private String answer;

    public ClassroomRatingData(Long id, double score, String answer) {
        this.id = id;
        this.score = score;
        this.answer = answer;
    }

    public ClassroomRatingData(ClassroomRating rating) {
        id = rating.getId();
        studentName = rating.getStudent().getName();
        studentId = rating.getStudent().getId();
        score = rating.getScore();
        protest = rating.getProtest();
        answer = rating.getAnswer();
    }


    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public long getStudentId() {
        return studentId;
    }

    public double getScore() {
        return score;
    }

    public String getProtest() {
        return protest;
    }

    public String getAnswer() {
        return answer;
    }
}
