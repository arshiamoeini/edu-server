package LOGIC;

import GUI.EducationalServicesDesigner;
import GUI.MainFrame;
import MODELS.*;
import database.UserTemp;
import shared.Identifier;
import shared.LoginResult;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private static Command instance;

    static {
        instance = new Command();
    }

    private UserTemp user = University.getInstance().getUser(12);
    private MainFrame mainFrame;

    public static Command getInstance() { return instance; }
    public static void setFrame(MainFrame mainFrame) {
        instance.mainFrame = mainFrame;
    }

    public void addRecommendation(int selectedProfessorIndex) {
        ProfessorTemp professor = getInstance().getFaculty().getProfessor(selectedProfessorIndex);
        professor.addRecommendation(user.getId());
        Logger.logInfo("Register recommendation for "+professor.getName()+" ,name of student: "+user.getName());
        //University.getInstance().getFaculty()
    }

    public LoginResult canLogin(Identifier date) {
        if (!date.isValidUserID()) { return LoginResult.WRONG_USER_ID; }

        user = University.getInstance().getUser(date.getUserID());
        if (user == null) { return LoginResult.WRONG_USER_ID; }

        if (user.getHashOfPassword() == date.getHashOfPassword()) {
            return LoginResult.PASS;
        } else {
            return LoginResult.WRONG_PASSWORD;
        }
    }

    public void showUserPage() {
        Logger.loginUser(user);
        //some second wait
        mainFrame.uploadPage();
    }
    public void showLoginUser() {
     //   mainFrame.showLogin();
    }

    protected boolean canUserEditSubjectsList(FacultyTemp faculty) {
        return  (user instanceof EducationalAssistantTemp && user.isIn(faculty));
    }
    public String getNameOfUser() {
        return user.getName();
    }

    public boolean isTheUserAProfessor() {
        return user instanceof ProfessorTemp;
    }

    protected UserTemp getUser() { return user; }
    protected EducationalAssistantTemp getEducationalAssistant() {
        try {
            return (EducationalAssistantTemp) user;
        } catch (Exception e) {
            Logger.logError("some not educational Assistante try to edit");
        }
        return null;
    }

    public ArrayList<EducationalServicesDesigner.WeeklyClassSchedule> getSchedule() {
        return Filter.getInstance().getSchedule(user.getWeeklyClasses());
    }

    public ArrayList<EducationalServicesDesigner.WeeklyClassSchedule> getExamSchedule() {
        return Filter.getInstance().getExamSchedule(user.getWeeklyClasses());
    }

    public String getNameOfUserFaculty() {
        return user.getFaculty().getMassage();
    }

    public void addWithdrawalRequest() {
        getFaculty().addEducationalRequest(FacultyTemp.RequestType.WITHDRAWAL,
                "not in good mod",
                user.getId());
        Logger.logInfo("Register withdrawal for user: "+user.getName());
    }

    public boolean isTheUSerABachelorStudent() {
        return user instanceof BachelorStudentTemp;
    }
    public boolean isTheUserAGraduateStudent() {
        return user instanceof GraduateStudentTemp;
    }

    public FacultyTemp getFaculty() {
        return user.getFaculty();
    }

    public ArrayList<String> getRecommendationsText() {
        if (user instanceof GraduateStudentTemp) {
            return ((GraduateStudentTemp) user).getRecommendationsText();
        } else if (user instanceof BachelorStudentTemp) {
            return ((BachelorStudentTemp) user).getRecommendationsText();
        }
        Logger.logError("not able to recommend a user with name: "+user.getName()+" and id:"+ user.getId());
        return null;
    }

    public ArrayList<BachelorStudentTemp.MajorRequest> getMajorRequests() {
        try {
            return ((BachelorStudentTemp) user).getMajorRequests();
        } catch (Exception e) {
            Logger.logError("not a bachelor student getting with user id: "+user.getId());
        }
        return null;
    }

    public void addMajor(int selectedIndex) {
        ((BachelorStudentTemp) user).addMajorRequest(selectedIndex);
        FacultyTemp endFaculty = University.getInstance().getFaculty(selectedIndex);
        getFaculty().addEducationalRequest(FacultyTemp.RequestType.MAJOR,
                endFaculty.getMassage(),
                user.getId());
        endFaculty.addEducationalRequest(FacultyTemp.RequestType.MAJOR,
                endFaculty.getMassage(),
                user.getId());
        Logger.logInfo("Register major to "+endFaculty.getMassage()+" for user: "+user.getName());
    }

    public String getUserID() {
        return String.valueOf(user.getId());
    }

    public void sendRecommendation(StudentTemp student, String text) {
        ((ProfessorTemp) user).removeRecommendation(student.getId());
        if (student instanceof BachelorStudentTemp) {
            ((BachelorStudentTemp) student).addAnsweredRecommendation(text);
        } else if (student instanceof GraduateStudentTemp) {
            ((GraduateStudentTemp) student).addAnsweredRecommendation(text);
        } else {
            System.out.println("wrong way send recommendation");
            Logger.logError("wrong way to send recommendation with user id: "+ user.getId());
        }
    }

    public List<StudentTemp> getRecommendedStudent() {
        if (user instanceof ProfessorTemp) {
            return ((ProfessorTemp) user).getRecommendedStudents();
        } else {
            Logger.logError("not professor want to get recommended student");
            System.out.println("werwr");
            return null;
        }
    }

    public boolean isTheUserAPHDStudent() {
        return user instanceof PhDStudentTemp;
    }
    public boolean isTheUserAEducationalAssistant() {
        return user instanceof EducationalAssistantTemp;
    }

    public void acceptingRequest(FacultyTemp.EducationalRequest request) {
        getEducationalAssistant().acceptRequest(request);
        Logger.logInfo("Accept educational request of type: "+request.getRequestType().getMassage()
                +" for student with id: "+request.getStudent().getId());
    }

    public void rejectingRequest(FacultyTemp.EducationalRequest request) {
        getEducationalAssistant().rejectRequest(request);
        Logger.logInfo("Reject educational request of type: "+request.getRequestType().getMassage()
                +" for student with id: "+request.getStudent().getId());
    }

    public ArrayList<Classroom.classRating> getRegisteredRecords() {
        return ((StudentTemp) user).getRegisteredRecords();
    }

    public List<Classroom> getClassrooms() {
        return user.getWeeklyClasses();
    }
}
