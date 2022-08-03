package MODELS;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EducationalAssistantTemp extends ProfessorTemp {
    public EducationalAssistantTemp(long id, String password) {
        super(id, password);
    }

    public void editClassroom(Classroom classroom,
                              int courseID,
                              int courseCredit,
                              String courseName,
                              ArrayList<Integer> prerequisite,
                              ArrayList<Integer> coRequisite,
                              int capacity,
                              int registrationNumber,
                              String professorName,
                              LocalDateTime dateTime) {
        //TODO edit courseID and professorName
        //TODO DELETE registrationNumber
        classroom.edit(capacity, professorName, dateTime);
        classroom.getCourse().edit(courseID, courseName, courseCredit, prerequisite, coRequisite);
        //TODO have object of class in hear from (show demo) maybe filter obj in command to help command
        //getFaculty().getClassroom()
    }

    public void acceptRequest(FacultyTemp.EducationalRequest request) {
        getFaculty().removeRequest(request);
        //TODO Handel
        if (request.getRequestType() == FacultyTemp.RequestType.MAJOR) {
            StudentTemp student = request.getStudent();
            if (student instanceof BachelorStudentTemp) {
                ((BachelorStudentTemp) student).acceptMajor(request.getMassage());
            } else {
                System.out.println("sdfaseeed");
            }
        }
    }

    public void rejectRequest(FacultyTemp.EducationalRequest request) {
        getFaculty().removeRequest(request);
        if (request.getRequestType() == FacultyTemp.RequestType.MAJOR) {
            StudentTemp student = request.getStudent();
            if (student instanceof BachelorStudentTemp) {
                ((BachelorStudentTemp) student).rejectMajor(request.getMassage());
            } else {
                System.out.println("sdfasd");
            }
        }
    }
}
