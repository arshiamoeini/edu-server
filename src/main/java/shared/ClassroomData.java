package shared;

import GUI.RowPanel;
import database.Classroom;
import database.Course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static GUI.RealTime.readLocalDateTime;

public class ClassroomData {

    private long id;
    private int courseId;
    private int credit;
    private String courseName;
    private List<Integer> prerequisite;
    private List<Integer> co_requisite;
    private int capacity;
    private int registrationNumber;
    private long professorId;
    private LocalDateTime examData;


    public ClassroomData(long id, RowPanel subjectRow, ArrayList<Integer> prerequisite, ArrayList<Integer> co_requisite) throws  Exception{
        this.id = id;
        this.prerequisite = prerequisite;
        this.co_requisite = co_requisite;
        courseId = Integer.parseInt(subjectRow.getText(2));
        credit = Integer.parseInt(subjectRow.getText(3));
        courseName = subjectRow.getText(4);
        capacity = Integer.parseInt(subjectRow.getText(6));
        registrationNumber = Integer.parseInt(subjectRow.getText(7));
        professorId = Long.parseLong(subjectRow.getText(8));
        examData = readLocalDateTime(subjectRow.getText(9));
    }
    public ClassroomData(Classroom classroom) {
        id = classroom.getId();
        courseId = classroom.getCourseId();
        credit = classroom.getCredit();
        courseName = classroom.getName();
        capacity = classroom.getCapacity();
        registrationNumber = classroom.getRegistrationNumber();
        professorId = classroom.getTeacherId();
        examData = classroom.getExamDate();
        if (classroom.getCourse() != null) {
            setRequisite(classroom.getCourse());
        } else {
            prerequisite = new ArrayList<>();
            co_requisite = new ArrayList<>();
        }
    }

    private void setRequisite(Course course) {
        prerequisite = course.getPrerequisite().stream().
            map(x -> x.getId()).
                collect(Collectors.toList());
        co_requisite = course.getCoRequisite().stream().
            map(x -> x.getId()).
                collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public Object[] getInRow() {
        return new Object[]{courseId, credit, courseName, prerequisite, co_requisite, capacity, registrationNumber, professorId, examData};
    }
}
