package client;

import GUI.Updatable;
import GUI.UserConstantInformation;
import com.google.gson.Gson;
import shared.RequestType;
import shared.Response;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Pulsator extends Thread {
    public static Pulsator instance;
    private Updatable currentPage = null;
    private static int ping = 100; //TODO Config
    private LocalTime time;
    private int countPulses = 0;
    private boolean offline = false;
    private String selectedFacultyName;

    public Pulsator() {
    }

    @Override
    public void run() {
        time = LocalTime.now();
        while (true) {
            try {
                Thread.sleep(2 * setAndGetPing(0));
            //    LocalDateTime x = LocalDateTime.now();
            //    System.out.println(time + " " +x + "   "+ (int) time.until(x, ChronoUnit.MILLIS));
                if (offline) {
                    continue;
                }
                if (countPulses < 5) {
                    sendPulse();
                } else {
                    offline = true;
                    UserConstantInformation.getInstance().addHandyButton( e -> {
                        reconnect();
                        offline = false;
                    });
                    setAndGetPing(2000); //TODO default check and show
                    System.out.println("your are offline");
                }
            } catch (InterruptedException e) {
                System.out.println("Pulsator has interupted");
                return;
                //throw new RuntimeException(e);
            }
        }
    }

    private void reconnect() {
        setAndGetPing(1000);
        UserConstantInformation.getInstance().getOutField().removeAll();
        addPulseThatGetNotResponseOrReset(true);
        Client.getInstance().autoLogin();
    }

    private void sendPulse() {
        RequestType requestType = setAndGetCurrentPage(null).getUpdateRequest();
        //  if (requestType == RequestType.GET_PROFESSORS_OF_SELECTED_FACULTY) {
        //      Client.getInstance().send(requestType, selectedFacultyName);
        // } else {
        if (requestType != null) {
            Client.getSender().send(requestType,
                    UserConstantInformation.getInstance().getUserId());
        }
        // }
        addPulseThatGetNotResponseOrReset(false);
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
            System.out.println("good");
        }
        setAndGetPing(Math.min((int) time.until(LocalTime.now(), ChronoUnit.MILLIS), 3000));//TODO Config
        time = LocalTime.now();
        addPulseThatGetNotResponseOrReset(true); //get response
    }


    public void setSelectedFacultyName(String selectedFacultyName) {
        this.selectedFacultyName = selectedFacultyName;
    }
}
