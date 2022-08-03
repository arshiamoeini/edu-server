package shared;

public enum UserType {
    MrMohseni(true),
    BachelorStudent(false),
    GraduateStudent(false),
    PHDStudent(false),
    EducationalAssistant(true),
    Professor(true),
    CampusChairmen(true);

    boolean professor;
    UserType(boolean professor) {
        this.professor = professor;
    }

    public boolean isProfessor() {
        return professor;
    }
}
