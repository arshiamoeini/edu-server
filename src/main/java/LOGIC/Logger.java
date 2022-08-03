package LOGIC;

import GUI.RealTime;
import database.UserTemp;

import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {
    public static String SOURCE = "src/main/resources/logs/";
    private static Logger instance;
    private static FileWriter fileWriter;

    public static Logger getInstance() {
        return instance;
    }

    public Logger() {
        try {
            fileWriter = new FileWriter(SOURCE + "log", true);
            logInfo("Start");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loginUser(UserTemp user) {
        try {
            fileWriter = new FileWriter(SOURCE + user.getId(), true);
            logInfo("Enter");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void logInfo(String text) {
        log("INFO    :"+text);
    }
    public static void logError(String text) {log("ERROR   :"+text); }
    public static void log(String text) {
        try {
            fileWriter.write(RealTime.dateAndTime(LocalDateTime.now())+" "+text+"\n");
            fileWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void Init() {
        instance = new Logger();
    }
}
