package shared;

import database.Classroom;

import java.util.Set;

public class WeeklyClassSchedule {
    String name;
    String teacher;
    String time;

    public WeeklyClassSchedule(String name, String teacher, String time) {
        this.name = name;
        this.teacher = teacher;
        this.time = time;
    }

    public WeeklyClassSchedule(Classroom classroom, boolean examData) {
        name = classroom.getName();
        teacher = classroom.getTeacher().getName();
        if (examData) {
            time = ""; //TODO
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %s", name, teacher, time);
    }
}
