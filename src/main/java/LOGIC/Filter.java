package LOGIC;

import GUI.*;
import MODELS.Classroom;
import MODELS.CourseTemp;
import MODELS.FacultyTemp;
import MODELS.University;

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

    private ArrayList<Classroom> subjects;

    private class SubjectFilterDate {
        public FacultyTemp faculty;
        public CourseTemp.Program program;
        public DemoList demoList;
        public SubjectFilterDate(FacultyTemp faculty, CourseTemp.Program program) {
            this.faculty = faculty;
            this.program = program;
        }
        public void addClassroomToFillList(Classroom classroom) {
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

        subjectFilterDate = new SubjectFilterDate(faculty, CourseTemp.Program.values()[programIndex]);
        getFilteredSubject(sortByExamDate);

        DemoList demoList;
        if (Command.getInstance().canUserEditSubjectsList(faculty)) {
            demoList = new EditableSubjectsList();
        } else {
            demoList = new SubjectsList();
        }

        subjectFilterDate.demoList = demoList;
        for (Classroom classroom: subjects) {
            subjectFilterDate.addClassroomToFillList(classroom);
        }
        return demoList;
    }
    private void getFilteredSubject(boolean sortByExamDate) {
        subjects.clear();
        for (Classroom classroom: subjectFilterDate.faculty.getClassrooms()) {
            if (classroom.getCourse().getProgram() == subjectFilterDate.program) {
                subjects.add(classroom);
            }
        }
        if (sortByExamDate) Collections.sort(subjects, new Comparator<Classroom>() {
            //TODO add this one varible
            @Override
            public int compare(Classroom o1, Classroom o2) {
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
        Classroom classroom = new Classroom(subjectFilterDate.faculty.lastAddedCourseID(),
                subjectFilterDate.faculty.getEducationalAssistantID(),
                0,
                LocalDateTime.now(), new ArrayList<>());
        subjectFilterDate.faculty.addClassrooms(classroom);

    }


    public ArrayList<EducationalServicesDesigner.WeeklyClassSchedule> getSchedule(List<Classroom> weeklyClasses) {
        ArrayList<EducationalServicesDesigner.WeeklyClassSchedule> ans = new ArrayList<>();
        for (Classroom classroom: weeklyClasses) {
            for (LocalDateTime time: classroom.getTime()) {
                ans.add(new EducationalServicesDesigner.WeeklyClassSchedule(
                        classroom.getCourse().getName(),
                        classroom.getProfessorName(),
                        RealTime.weekDayAndTime(time)));
            }
        }
        return ans;
    }

    public ArrayList<EducationalServicesDesigner.WeeklyClassSchedule> getExamSchedule(List<Classroom> weeklyClasses) {
        ArrayList<EducationalServicesDesigner.WeeklyClassSchedule> ans = new ArrayList<>();
        Collections.sort(weeklyClasses, new Comparator<Classroom>() {
            @Override
            public int compare(Classroom o1, Classroom o2) {
                if (o1 == null || o2 == null) return 0;
                return o1.getExamDate().compareTo(o2.getExamDate());
            }
        });
        for (Classroom classroom: weeklyClasses) {
            ans.add(new EducationalServicesDesigner.WeeklyClassSchedule(
                        classroom.getCourse().getName(),
                        classroom.getProfessorName(),
                        RealTime.dateAndTime(classroom.getExamDate())));
        }
        return ans;
    }
}
