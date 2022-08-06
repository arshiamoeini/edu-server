package client;

import GUI.Login;
import GUI.LoginTemp;
import GUI.MainFrame;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.init();
    /*    Pulsator.getInstance();
        System.out.println(Pulsator.getInstance().isAlive());
        Pulsator.instance.wait();
        System.out.println(Pulsator.instance.isAlive());
        Pulsator.instance.start();
        System.out.println(Pulsator.instance.isAlive());*/
    }
}
