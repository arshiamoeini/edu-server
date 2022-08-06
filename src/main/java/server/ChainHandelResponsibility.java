package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.Classroom;
import database.CourseView;
import database.Database;
import net.bytebuddy.ByteBuddy;
import shared.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    private Function<Request, Request> getRequestedData = x -> {
        switch (x.getType()) {
            case GET_WEEKLY_CLASS_DATA:
                return getWeeklyClassData.apply(x);
            case GET_COURSE_VIEWS:
                return getCourseViewsData.apply(x);
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
        Request response = (request.getType() == RequestType.CHECK_CONNECTION ?
                new Request(null, new ArrayList<>()) :
                getRequestedData.apply(request));
        server.send(id, response.getData().stream().toArray(String[]::new));
    }
}
