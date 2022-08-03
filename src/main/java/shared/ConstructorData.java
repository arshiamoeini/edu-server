package shared;

public class ConstructorData {
    private UserType type;
    private String[] factories;
    private String[] programs;

    public ConstructorData(UserType type, String[] factories, String[] programs) {
        this.type = type;
        this.factories = factories;
        this.programs = programs;
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
}
