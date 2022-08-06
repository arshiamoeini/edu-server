package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.Response;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.T;

public class ResponseHandler extends Thread {
    private final InputStream in;
    private Gson gson;
    public ResponseHandler(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        while (true) {
            try {
                listening(new Scanner(in));
            } catch (NoSuchElementException e) {
                System.out.println("disconnected to server");
                return;
            }
        }
    }
    private void listening(Scanner scanner) throws NoSuchElementException {
        while (true) {
            String responseStr = scanner.nextLine();
            Response response = gson.fromJson(responseStr, Response.class);
            new Thread(() -> Pulsator.getInstance().updateData(response, gson)).start();
        }
    }
}
