package MODELS;

import database.UserTemp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StudentTemp extends UserTemp {
   // @Override
   // public  Class<? extends User> getType() {
    //    return Student.class;
   // }

    public enum EducationalStatus implements ContainMessage {
        ALLOWED_TO_REGISTER("Allowed to register"),
        PUT_OF("put of");

        private String message;

        EducationalStatus(String message) {
            this.message = message;
        }

        @Override
        public String getMassage() {
            return message;
        }
    }

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
    public ArrayList<Classroom.classRating> getRegisteredRecords() {
        ArrayList<Classroom.classRating> ans = new ArrayList<>();
        FacultyTemp faculty = getFaculty();
        for (int classroomID: weeklyClassesID) {
            Classroom classroom = faculty.getClassroom(classroomID);
            if (classroom.isRegistered()) {
                ans.add(classroom.getRatingWithID(getId()));
            }
        }
        return ans;
    }
}
