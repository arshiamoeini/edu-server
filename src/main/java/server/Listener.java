package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

public class Listener implements Runnable {
    private String authToken;
    private int id;
    private InputStream in;
    private Gson gson;
    public Listener(InputStream in, Function<String, Integer> identification) throws IOException {
        this.in = in;
        authToken = AuthTokenGenerator.generateNewToken();
        id = identification.apply(authToken);
    }

    @Override
    public void run() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        while (true) {
            try {
                listening(new Scanner(in));
            } catch (NoSuchElementException e) {
                System.out.println("disconnected");
                return;
            }
        }
    }

    private void listening(Scanner scanner) {
        while (true) {
            String identifier = scanner.nextLine();
            if (identifier.equals(authToken)) {
                Request request = gson.fromJson(scanner.nextLine(), Request.class);
                ChainHandelResponsibility.getInstance().handel(id, request);
            }
        }
    }
}
