package LOGIC;

import GUI.*;
import MODELS.*;
import shared.Program;
import shared.WeeklyClassSchedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Filter {
    private static Filter instance;

    static {
        instance = new Filter();
    }

    private ArrayList<ClassroomTemp> subjects;

    private class SubjectFilterDate {
        public FacultyTemp faculty;
        public Program program;
        public DemoList demoList;
        public SubjectFilterDate(FacultyTemp faculty, Program program) {
            this.faculty = faculty;
            this.program = program;
        }
        public void addClassroomToFillList(ClassroomTemp classroom) {
            demoList.addSubjectRow(
                    classroom.getCourse().getId(),
                    classroom.getCourse().getCredit(),
                    classroom.getCourse().getName(),
                    classroom.getCourse().getPrerequisite(),
                    classroom.getCourse().getCoRequisite(),
                    classroom.getCapacity(),
                    classroom.getRegistrationNumber(),
                    classroom.getProfessorName(),
                    classroom.getExamDate());
           // demoList.revalidate();
        }
    }
    SubjectFilterDate subjectFilterDate;

    public static Filter getInstance() {
        return instance;
    }

    public Filter() {
        subjects = new ArrayList<>();
    }

    public DemoList doSubjectFilter(int facultyIndex, int programIndex, boolean sortByExamDate) {
        FacultyTemp faculty = University.getInstance().getFaculty(facultyIndex);

        subjectFilterDate = new SubjectFilterDate(faculty, Program.values()[programIndex]);
        getFilteredSubject(sortByExamDate);

        DemoList demoList;
        if (Command.getInstance().canUserEditSubjectsList(faculty)) {
            demoList = new EditableSubjectsListTemp();
        } else {
            demoList = new SubjectsListTemp();
        }

        subjectFilterDate.demoList = demoList;
        for (ClassroomTemp classroom: subjects) {
            subjectFilterDate.addClassroomToFillList(classroom);
        }
        return demoList;
    }
    private void getFilteredSubject(boolean sortByExamDate) {
        subjects.clear();
        for (ClassroomTemp classroom: subjectFilterDate.faculty.getClassrooms()) {
            if (classroom.getCourse().getProgram() == subjectFilterDate.program) {
                subjects.add(classroom);
            }
        }
        if (sortByExamDate) Collections.sort(subjects, new Comparator<ClassroomTemp>() {
            //TODO add this one varible
            @Override
            public int compare(ClassroomTemp o1, ClassroomTemp o2) {
                if (o1 == null || o2 == null) return 0;
                return o1.getExamDate().compareTo(o2.getExamDate());
            }
        });
    }

    public void setSubjectEdit(int row,
                               int id,
                               int credit,
                               String name,
                               ArrayList<Integer> prerequisite,
                               ArrayList<Integer> coRequisite,
                               int capacity,
                               int registrationNumber,
                               String professorName,
                               LocalDateTime dateTime) {
        Logger.logInfo("Edit subject of name: "+subjects.get(row - 1).getCourse().getName());
        Command.getInstance().getEducationalAssistant().
                editClassroom(subjects.get(row - 1), id, credit, name, prerequisite, coRequisite, capacity, registrationNumber, professorName, dateTime);
        ListDesigner.getInstance().showSubjectsList();
    }

    public void addNewSubject() {
        int counter = subjectFilterDate.faculty.getCourses().size();
        int pre = subjectFilterDate.faculty.getID();
        subjectFilterDate.faculty.
                addCourses("", pre * 100 + counter, new ArrayList<>(), new ArrayList<>(),
                        subjectFilterDate.program);
        ClassroomTemp classroom = new ClassroomTemp(subjectFilterDate.faculty.lastAddedCourseID(),
                subjectFilterDate.faculty.getEducationalAssistantID(),
                0,
                LocalDateTime.now(), new ArrayList<>());
        subjectFilterDate.faculty.addClassrooms(classroom);

    }


    public ArrayList<WeeklyClassSchedule> getSchedule(List<ClassroomTemp> weeklyClasses) {
        ArrayList<WeeklyClassSchedule> ans = new ArrayList<>();
        for (ClassroomTemp classroom: weeklyClasses) {
            for (LocalDateTime time: classroom.getTime()) {
                ans.add(new WeeklyClassSchedule(
                        classroom.getCourse().getName(),
                        classroom.getProfessorName(),
                        RealTime.weekDayAndTime(time)));
            }
        }
        return ans;
    }

    public ArrayList<WeeklyClassSchedule> getExamSchedule(List<ClassroomTemp> weeklyClasses) {
        ArrayList<WeeklyClassSchedule> ans = new ArrayList<>();
        Collections.sort(weeklyClasses, new Comparator<ClassroomTemp>() {
            @Override
            public int compare(ClassroomTemp o1, ClassroomTemp o2) {
                if (o1 == null || o2 == null) return 0;
                return o1.getExamDate().compareTo(o2.getExamDate());
            }
        });
        for (ClassroomTemp classroom: weeklyClasses) {
            ans.add(new WeeklyClassSchedule(
                        classroom.getCourse().getName(),
                        classroom.getProfessorName(),
                        RealTime.dateAndTime(classroom.getExamDate())));
        }
        return ans;
    }
}
