package shared;

import database.CourseView;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CourseViewData {
    private int CourseID;
    private String name;
    private LocalDateTime finalExam;
    private ArrayList<LocalDateTime> exercises;

    public CourseViewData(CourseView courseView) {
        name = courseView.getName();
        finalExam = courseView.getExamDate();
    }

    public int getID() {
        return CourseID;
    }
    public String getName() {
        return name;
    }
}
