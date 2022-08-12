package shared;

public class ConstructorData {
    private UserType type;
    private String[] factories;
    private String[] programs;
    private String facultyName;

    public ConstructorData(UserType type, String[] factories, String[] programs, String facultyName) {
        this.type = type;
        this.factories = factories;
        this.programs = programs;
        this.facultyName = facultyName;
    }

    public UserType getType() {
        return type;
    }
    public String[] getFactories() {
        return factories;
    }
    public String[] getPrograms() {
        return programs;
    }
    public String getFacultyName() {
        return facultyName;
    }
}
