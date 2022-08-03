package client;

import GUI.Login;
import GUI.LoginTemp;
import GUI.MainFrame;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.init();
    }
}
