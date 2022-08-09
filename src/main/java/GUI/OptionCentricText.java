package GUI;

import MODELS.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class OptionCentricText extends JComboBox {
    public static String[] faculties;
    public static String[] programs;


    public enum OptionsFrom {
        Faculties,
        MasterLevel,
        Program,
        Professor
    }

    public OptionCentricText(Object[] items) {
        super(items);
    }

    public OptionCentricText(OptionsFrom optionsFrom) {
        this(getOptionsName(optionsFrom));
    }
    public OptionCentricText(FacultyTemp faculty) {
        this(faculty.getProfessorsName());
    }
    public OptionCentricText(List<ClassroomTemp> classroom) {
        this(classroom.stream().map(ClassroomTemp::getCourse).map(CourseTemp::getName).toArray());
    }

    public static Object[] getOptionsName(OptionsFrom optionsFrom) {
        switch (optionsFrom) {
            case Faculties:
                return faculties;
            case MasterLevel:
                return (Arrays.stream(ProfessorTemp.MasterLevel.values()).
                        map(ProfessorTemp.MasterLevel::getMassage).toArray());
            case Program:
                return (Arrays.stream(CourseTemp.Program.values()).
                        map(CourseTemp.Program::getMassage).toArray());
        }
        return new Object[0];
    }

    public static void setFaculties(String[] faculties) {
        OptionCentricText.faculties = faculties;
    }
    public static void setPrograms(String[] programs) {
        OptionCentricText.programs = programs;
    }
    public String getSelectedItemName() {
        return (String) getItemAt(getSelectedIndex());
    }
}