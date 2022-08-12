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
        Database.getInstance().closeSession();
        return new Request(null, timesData, timesExam);
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
    private Function<Request, Request> addMajorRequest = x -> {
        BachelorStudent student = (BachelorStudent) Database.getInstance().getStudent(Long.parseLong(x.getLastData()));
        MajorRequest majorRequest = new MajorRequest(Database.getInstance().getFacultyByName(x.getLastData()));
        sendMajorNotifications(student, majorRequest);
        Database.getInstance().addMajorRequest(student, majorRequest);
        Database.getInstance().closeSession();
        return null;
    };
    private Function<Request, Request> addDefaultClassroom = x -> {
    //    Database.getInstance().addDefaultClassroom(x.getData().get(0), x.getData().get(1));
        Database.getInstance().closeSession();
        return null;
    };
    private Function<Request, Request> addStudent = x -> {
        Faculty faculty = new Faculty(x.getLastData());
        int result = -1;
        if (faculty != null) {
            result = Database.getInstance().addStudent(faculty, gson.fromJson(x.getLastData(), String[].class));
            Database.getInstance().closeSession();
        }
        return null; //TODO
    };

    private void sendMajorNotifications(BachelorStudent student, MajorRequest majorRequest) {
        EducationalAssistant sourceFacultyAssistant = student.getFaculty().getAssistant();
        EducationalAssistant majoredFacultyAssistant = majorRequest.getTargetFaculty().getAssistant();
        String majoredFacultyName = majorRequest.getTargetFaculty().getName();
        Database.getInstance().addNotification(sourceFacultyAssistant, getMajorNotification(majoredFacultyName, student, sourceFacultyAssistant));
        Database.getInstance().addNotification(majoredFacultyAssistant, getMajorNotification(majoredFacultyName, student, sourceFacultyAssistant));
    }

    private Notification getMajorNotification(String facultyName, BachelorStudent student, EducationalAssistant assistant) {
        return new Notification(NotificationType.MAJOR_REQUEST,
                "major request",
                student.getId(),
                String.format("my id is %s and I wan,t to major with %s faculty", student.getId(), facultyName),
                student.getName());
    }

    private Notification getTurnToDefendTheDissertationNotification(User user) {
        return new Notification(NotificationType.INFO,
                "your turn decided", 0, String.format("On %s you can defend your dissertation.",
                RealTime.dateAndTime(LocalDateTime.now().plusDays(12))), "sharif");
    }

    private Notification getWithdrawalNotification(User user, Student student) {
        return new Notification(NotificationType.WITHDRAWAL,
                "withdrawal from education",
                student.getId(),
                "can,t continue",
                student.getName());
    }

    private Notification getNotificationForCertificate(User user, String text, Faculty faculty) {
        return new Notification(NotificationType.INFO,
                "certificate",
                faculty.getAssistantId(),
                text,
                faculty.getName());
    }

    private Notification getNotificationForRecommendation(Professor professor, User user) {
        return new Notification(NotificationType.RECOMMENDATION,
                "recommendation",
                user.getId(),
                getRecommendedText(professor.getName(), user),
                user.getName());
    }

    private String getRecommendedText(String professorName, User student) {
        return String.format("I %s certify that Mr. / Mrs. %s with student number %s, \n has completed courses ... \n with a grade of ... \n and has also worked as a teaching assistant in courses ... .",
                professorName, //TODO Config
                student.getName(),
                student.getId());
    }

    private Function<Request, Request> getProfessors = x -> {
            Faculty faculty = Database.getInstance().getFacultyByName(x.getLastData());
            ProfessorData[] professorsData = Database.getInstance().getProfessors(faculty).stream()
                    .map(p -> new ProfessorData(p)).toArray(ProfessorData[]::new);//p.getName() == null ? "null" : p.getName())).toArray(String[]::new);
            return new Request(null, gson.toJson(professorsData));
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
    private Function<Request, Request> getClassRoomWithFilter = x -> {
        Stream<Classroom> classroomStream = getFilteredClassroom(x);
        if (Boolean.valueOf(x.getLastData())) {
            classroomStream = classroomStream.sorted((o1, o2) -> {
                if (o1 == null || o2 == null || o1.getExamDate() == null || o2.getExamDate() == null) return 0;
                return o1.getExamDate().compareTo(o2.getExamDate());
            });//TODO add this one varible
        }
        Request request = new Request(null, gson, classroomStream.map(c -> new ClassroomData(c)).toArray());
        Database.getInstance().closeSession();
   //     List<Classroom> classrooms = classroomStream.collect(Collectors.toList());
        return request;
    };

    private Stream<Classroom> getFilteredClassroom(Request x) {
        Faculty faculty = Database.getInstance().getFacultyByName(x.getLastData());
        Program program = Program.values()[Integer.parseInt(x.getLastData())];
        return Database.getInstance().getClassrooms(faculty).stream().
                filter(c -> c.getProgram() == program);
    }

    private Function<Request, Request> getUserMainPageData = x -> {
        long id = Long.parseLong(x.getLastData());
        User user = Database.getInstance().getUser(id);
        try {
            String nationalCode = String.valueOf(user.getNationalCode());
            String phoneNumber = String.valueOf(user.getPhoneNumber());
            Request request = new Request(user.getName(), user.getEmail(), nationalCode, phoneNumber, user);
            return request;
        } catch (Exception e) {
            System.out.println("why");
            return null;
        }
    };
    private Function<Request, Request> getProfessorMainPageData = getUserMainPageData.andThen(x -> {
            Professor professor = (Professor) x.getLast();
            x.addData(gson, professor.getRoomNumber());
            x.addData(String.valueOf(professor.getMasterLevel()));
            Database.getInstance().closeSession();
            return x;
    });
    private Function<Request, Request> getStudentMainPageData = getUserMainPageData.andThen(x -> {
        Student student = (Student) x.getLast();
        x.addData(gson, student.isRegistrationLicense());
        x.addData(gson, student.getRegistrationTime());
        addSpecialField(x, student);
        Database.getInstance().closeSession();
        return x;
    });

    private void addSpecialField(Request x, Student student) {
        if (student.getSupervisor() == null) {
            x.addData("no one");
        } else {
            x.addData(gson, student.getSupervisor().getName());
        }
        x.addData("null"); //TODO
        x.addData(String.valueOf(student.getProgram()));
        x.addData(String.valueOf(student.getEducationalStatus()));
    }

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
            case GET_ClASS_ROOM_WITH_FILTER:
                return getClassRoomWithFilter.apply(x);
            case GET_PROFESSOR_MAIN_PAGE_DATA:
                return getProfessorMainPageData.apply(x);
            case GET_STUDENT_MAIN_PAGE_DATA:
                return getStudentMainPageData.apply(x);
            default:
                return new Request(null, new ArrayList<>());
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
            server.send(id, response.getData().stream().toArray());
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
            case ADD_MAJOR_REQUEST:
                addMajorRequest.apply(request);
                break;
            case ADD_DEFAULT_CLASSROOM:
                addDefaultClassroom.apply(request);
                break;
            case ADD_STUDENT:
                addStudent.apply(request);
                break;
        }
    }
}
