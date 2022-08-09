package server;

import GUI.RealTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.*;
import shared.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class ChainHandelResponsibility {
    private static ChainHandelResponsibility instance;
    private Server server;
    private Gson gson;
    private Function<Request, Request> getWeeklyClassData = x -> {
        String userId = x.getLastData();
        Set<Classroom> classrooms = Database.getInstance().getWeeklyClassrooms(Long.parseLong(userId));
        String timesData = ArrayToString(classrooms.stream().map(cl -> (new WeeklyClassSchedule(cl, false))));
        String timesExam = ArrayToString(classrooms.stream().map(cl -> (new WeeklyClassSchedule(cl, true))));
        Request request = new Request(null, new ArrayList<>());
        request.addData(timesData);
        request.addData(timesExam);
        Database.getInstance().closeSession();
        return request;
    };
    private Function<Request, Request> getCourseViewsData = x -> {
        String userId = x.getLastData();
        Set<CourseView> courseViews = Database.getInstance().getCourseViews(Long.parseLong(userId));
        String data = gson.toJson(courseViews.stream().map(cl -> new CourseViewData(cl)).toArray(CourseViewData[]::new), CourseViewData[].class);//ArrayToString(courseViews.stream().map(cl -> new CourseViewData(cl)));
        Request request = new Request(null, new ArrayList<>(Collections.singleton(data)));
        Database.getInstance().closeSession();
        return request;
    };
    private Function<Request, Request> getNotifications = x -> {
        String userId = x.getLastData();
        List<Notification> notifications = Database.getInstance().getNotifications(Long.parseLong(userId));
        String data = gson.toJson(notifications.stream()
                .map(nf -> new NotificationData(nf, nf.isInfo(), nf.isEditable()))
                .toArray(NotificationData[]::new), NotificationData[].class);
        Request request = new Request(null, new ArrayList<>(Collections.singleton(data)));
        Database.getInstance().closeSession();
        return request;

    };
    private Function<Request, Request> addRecommendation = x -> {
        User user = Database.getInstance().getUser(Long.parseLong(x.getLastData()));
        Faculty faculty = Database.getInstance().getFacultyByName(x.getLastData());
        Professor professor = faculty.getProfessors().get(Integer.parseInt(x.getLastData()));
        Database.getInstance().addNotification(professor, getNotificationForRecommendation(professor, user));
        Database.getInstance().closeSession();
        return null;
    };
    private Function<Request, Request> addCertificate = x -> {
        User user = Database.getInstance().getUser(Long.parseLong(x.getLastData()));
        Faculty faculty =  ((Student) user).getFaculty();
        String text = String.format("It is certified that Mr. / Mrs. %s with student number %s \nis studying in %s field at Sharif University. \n Certificate validity date: %s",
                user.getName(),
                user.getId(),
                faculty.getName(),
                RealTime.dateAndTime(LocalDateTime.now().plusYears(4)));
        Database.getInstance().addNotification(user, getNotificationForCertificate(user, text, faculty));
        Database.getInstance().closeSession();
        return null;
    };
    private Function<Request, Request> addWithdrawalRequest = x -> {
        Student student = Database.getInstance().getStudent(Long.parseLong(x.getLastData()));
        EducationalAssistant educationalAssistant = student.getFaculty().getAssistant();
        Database.getInstance().addNotification(educationalAssistant,
                getWithdrawalNotification(educationalAssistant, student));
        Database.getInstance().closeSession();
        return null;
    };
    private Function<Request, Request> addTurnToDefendTheDissertation = x -> {
        User user = Database.getInstance().getUser(Long.parseLong(x.getLastData()));
        Database.getInstance().addNotification(user, getTurnToDefendTheDissertationNotification(user));
        Database.getInstance().closeSession();
        return null;
    };
    private Function<Request, Request> addAccommodationRequest = x -> {
        return null; //TODO
    };

    private Notification getTurnToDefendTheDissertationNotification(User user) {
        return new Notification(NotificationType.INFO,
                "your turn decided", 0, String.format("On %s you can defend your dissertation.",
                RealTime.dateAndTime(LocalDateTime.now().plusDays(12))), "sharif", user);
    }

    private Notification getWithdrawalNotification(User user, Student student) {
        return new Notification(NotificationType.WITHDRAWAL,
                "withdrawal from education",
                student.getId(),
                "can,t continue",
                student.getName(),
                user);
    }

    private Notification getNotificationForCertificate(User user, String text, Faculty faculty) {
        return new Notification(NotificationType.INFO,
                "certificate",
                faculty.getId(),
                text,
                faculty.getName(),
                user);
    }

    private Notification getNotificationForRecommendation(Professor professor, User user) {
        return new Notification(NotificationType.RECOMMENDATION,
                "recommendation",
                user.getId(),
                getRecommendedText(professor.getName(), user),
                user.getName(),
                professor);
    }

    private String getRecommendedText(String professorName, User student) {
        return String.format("I %s certify that Mr. / Mrs. %s with student number %s, \n has completed courses ... \n with a grade of ... \n and has also worked as a teaching assistant in courses ... .",
                professorName, //TODO Config
                student.getName(),
                student.getId());
    }

    private Function<Request, Request> getProfessors = x -> {
            Faculty faculty = Database.getInstance().getFacultyByName(x.getLastData());
            String[] professorsName = faculty.getProfessors().stream()
                    .map(p -> p.getName()).toArray(String[]::new);
            return new Request(null, gson, professorsName);
    };
    private Function<Request, Request> getMajorsStatus = x -> {
        BachelorStudent bachelorStudent = (BachelorStudent) Database.getInstance().getStudent(Long.parseLong(x.getLastData()));
        ArrayList<String> majoredFacultiesName = new ArrayList<>();
        ArrayList<String> majorsStatus = new ArrayList<>();
        bachelorStudent.getMajorRequests().stream().forEach(major -> {
            majoredFacultiesName.add(major.getTargetFaculty().getName());
            majorsStatus.add(major.getStatus());
        });
        return new Request(null, gson, majoredFacultiesName.toArray(), majorsStatus.toArray());
    };
    private Function<Request, Request> getRequestedData = x -> {
        switch (x.getType()) {
            case GET_WEEKLY_CLASS_DATA:
                return getWeeklyClassData.apply(x);
            case GET_COURSE_VIEWS:
                return getCourseViewsData.apply(x);
            case GET_NOTIFICATIONS:
                return getNotifications.apply(x);
            case GET_PROFESSORS_OF_SELECTED_FACULTY:
                return getProfessors.apply(x);
            case GET_MAJORS_STATUS:
                return getMajorsStatus.apply(x);
            default:
                return null;
        }
    };
    <T> String ArrayToString(Stream<T> stream) {
        return gson.toJson(stream.map(x -> gson.toJson(x)).toArray(String[]::new));
    }

    public ChainHandelResponsibility(Server server) {
        this.server = server;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public static void build(Server server) {
        instance = new ChainHandelResponsibility(server);
    }

    public synchronized static ChainHandelResponsibility getInstance() {
        return instance;
    }

    public synchronized void handel(int id, Request request) {
        if (request.getType().isNeedReply()) {
            Request response = (request.getType() == RequestType.CHECK_CONNECTION ?
                    new Request(null, new ArrayList<>()) :
                    getRequestedData.apply(request));
            server.send(id, response.getData().stream().toArray(String[]::new));
        } else {
            doRequestTask(request);
        }
    }

    private void doRequestTask(Request request) {
        switch (request.getType()) {
            case ADD_RECOMMENDATION:
                addRecommendation.apply(request);
                break;
            case ADD_CERTIFICATE:
                addCertificate.apply(request);
                break;
            case ADD_WITHDRAWAL_REQUEST:
                addWithdrawalRequest.apply(request);
                break;
            case ADD_REQUEST_TURN_TO_DEFEND_THE_DISSERTATION:
                addTurnToDefendTheDissertation.apply(request);
                break;
            case ADD_ACCOMMODATION_REQUEST:
                addAccommodationRequest.apply(request);
                break;
        }
    }
}
