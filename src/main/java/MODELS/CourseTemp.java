package MODELS;

import shared.Program;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CourseTemp {
    private int facultyID;

    Program program;
    private String name;
    private int id;
    private int credit;
    private ArrayList<CourseTemp> prerequisite;
    private ArrayList<CourseTemp> coRequisite;

    public CourseTemp(int FacultyID, String name, int id, ArrayList<CourseTemp> prerequisite, ArrayList<CourseTemp> coRequisite, Program program) {
        this.facultyID = facultyID;
        this.name = name;
        this.id = id;
        this.prerequisite = prerequisite;
        this.coRequisite = coRequisite;
        this.program = program;
    }


    public void edit(int id, String courseName, int courseCredit, ArrayList<Integer> prerequisite, ArrayList<Integer> coRequisite) {
        FacultyTemp faculty = getFaculty();
        this.name = courseName;
        this.credit = courseCredit;
        this.prerequisite = new ArrayList<>(prerequisite.stream().map(x -> faculty.getCourseWithLongID(x))
                .collect(Collectors.toList()));
        this.coRequisite = new ArrayList<>(coRequisite.stream().map(x -> faculty.getCourseWithLongID(x))
                .collect(Collectors.toList()));
        this.id = id;
    }

    private FacultyTemp getFaculty() {
        return University.getInstance().getFaculty(facultyID);
    }

    public Program getProgram() { return program; }
    public int getId() {
        return id;
    }
    public int getCredit() {
        return credit;
    }

    public String getName() { return name; }

    public ArrayList<Integer> getPrerequisite() {
        return getCoursesID(prerequisite);
    }
    public ArrayList<Integer> getCoRequisite() {
        return getCoursesID(coRequisite);
    }
    private ArrayList<Integer> getCoursesID(ArrayList<CourseTemp> courses) {
        return new ArrayList<>(
                courses.stream().map(CourseTemp::getId).collect(Collectors.toList()));
    }
}
