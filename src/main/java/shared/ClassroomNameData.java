package shared;

import database.Professor;

public class ClassroomNameData {
    private long id;
    private String courseName;
    private String teacherName;
    private boolean editScore;
    private boolean answerStudent;

    public ClassroomNameData(long id, String courseName, String teacherName, boolean editScore, boolean answerStudent) {
        this.id = id;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.editScore = editScore;
        this.answerStudent = answerStudent;
    }

    public ClassroomNameData(Long id, String name, String teacherName, long teacherId, boolean registered, boolean isaFinal, Professor professor) {
        this(id, name, teacherName,
                canEditScore(professor, teacherId, isaFinal),
                canAnswerStudent(professor, teacherId, registered, isaFinal));
    }

    private static boolean canAnswerStudent(Professor professor, long teacherId, boolean registered, boolean isaFinal) {
        return registered && !isaFinal && professor.getId() == teacherId;
    }

    private static boolean canEditScore(Professor professor, long teacherId, boolean isaFinal) {
        return !isaFinal && professor.getId() == teacherId;
    }

    public long getId() {
        return id;
    }

    public boolean isEditScore() {
        return editScore;
    }

    public boolean isAnswerStudent() {
        return answerStudent;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
