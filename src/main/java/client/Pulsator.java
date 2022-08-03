package client;

import GUI.Updatable;

public class Pulsator extends Thread {
    private static Pulsator instance;
    private Updatable currentPage = null;
    private static int ping = 100; //TODO Config
    public Pulsator() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(ping);
                Client.getSender().send(setAndGetCurrentPage(null).getUpdateRequest());

            } catch (InterruptedException e) {
                System.out.println("Pulsator has interupted");
                return;
                //throw new RuntimeException(e);
            }
        }
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

    public synchronized Updatable setAndGetCurrentPage(Updatable newPage) {
        if (newPage == null) return currentPage;
        currentPage = newPage;
        return null;
    }
}
