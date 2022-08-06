package client;

import GUI.Updatable;
import GUI.UserConstantInformation;
import com.google.gson.Gson;
import shared.Response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Pulsator extends Thread {
    public static Pulsator instance;
    private Updatable currentPage = null;
    private static int ping = 100; //TODO Config
    private LocalTime time;
    private int countPulses = 0;
    private boolean offline = false;
    public Pulsator() {
    }

    @Override
    public void run() {
        time = LocalTime.now();
        while (true) {
            try {
                Thread.sleep(setAndGetPing(0));
            //    LocalDateTime x = LocalDateTime.now();
            //    System.out.println(time + " " +x + "   "+ (int) time.until(x, ChronoUnit.MILLIS));
                if (offline) {
                    continue;
                }
                if (true) {//countPulses < 5) {
                    Client.getSender().send(setAndGetCurrentPage(null).getUpdateRequest(),
                            UserConstantInformation.getInstance().getUserId());
                    addPulseThatGetNotResponseOrReset(false);
                } else {
                    offline = true;
                    ping = 2000; //TODO default check and show
                    System.out.println("your are offline");
                }
            } catch (InterruptedException e) {
                System.out.println("Pulsator has interupted");
                return;
                //throw new RuntimeException(e);
            }
        }
    }

    private synchronized void addPulseThatGetNotResponseOrReset(boolean reset) {
        if (reset) countPulses = 0;
        else ++countPulses;
    }

    public static Pulsator getInstance() {
        if (instance == null || !instance.isAlive()) {
            instance = new Pulsator();
            instance.start();
        }
        return instance;
    }

    public void update(Updatable updatable) {
        setAndGetCurrentPage(updatable);
    }

    private synchronized Updatable setAndGetCurrentPage(Updatable newPage) {
        if (newPage == null) return currentPage;
        currentPage = newPage;
        return null;
    }
    private synchronized int setAndGetPing(int newPing) {
        if (newPing == 0) return ping;
        ping = newPing;
        return ping;
    }
    public void updateData(Response response, Gson gson) {
        try {
            setAndGetCurrentPage(null).update(response.getData(), gson);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
        setAndGetPing(Math.min((int) time.until(LocalTime.now(), ChronoUnit.MILLIS), 3000));//TODO Config
        time = LocalTime.now();
        addPulseThatGetNotResponseOrReset(true); //get response
    }


}
