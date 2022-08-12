package MODELS;

import database.UserTemp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StudentTemp extends UserTemp {
   // @Override
   // public  Class<? extends User> getType() {
    //    return Student.class;
   // }

    public EducationalStatus educationalStatus;
    private ProfessorTemp Supervisor;
    private boolean registrationLicense;
    private LocalDateTime registrationTime;

    public StudentTemp(long id, String password) {
        super(id, password);
    }

    public void setSupervisor(ProfessorTemp supervisor) {
        Supervisor = supervisor;
    }
    public ProfessorTemp getSupervisor() {
        return Supervisor;
    }
    public ArrayList<ClassroomTemp.classRating> getRegisteredRecords() {
        ArrayList<ClassroomTemp.classRating> ans = new ArrayList<>();
        FacultyTemp faculty = getFaculty();
        for (int classroomID: weeklyClassesID) {
            ClassroomTemp classroom = faculty.getClassroom(classroomID);
            if (classroom.isRegistered()) {
                ans.add(classroom.getRatingWithID(getId()));
            }
        }
        return ans;
    }
}
