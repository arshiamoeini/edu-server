package client;

import GUI.*;
import LOGIC.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Client implements Sender {
    private static Client instance;
    String authToken;
    Socket socket;
    PrintWriter out;
    InputStream in;
    TreeSet<Integer> cards = new TreeSet<>();

    Pulsator client;
    MainFrame frame;
    Gson gson;
    Runnable logout = () -> {

    };
    Scanner scanner;
    private ResponseHandler responseHandler;

    private Identifier passDate = new Identifier("1003", "hello");
    private Callable<LoginResult> connectSocket = () -> {
        try {
            socket = new Socket("localhost", 8000); //TODO config

            out = new PrintWriter(socket.getOutputStream());
            in = socket.getInputStream();
            return LoginResult.PASS;
        } catch (IOException e) {
            System.out.println("cant connect!");
            return LoginResult.DISCONNECTED;
            //throw new RuntimeException(e);
        }
    };
    private Callable<LoginResult> catchLoginResult = () -> {
    //    System.out.println("im here");
        scanner = new Scanner(in);
    //    authToken = scanner.nextLine();
        return LoginResult.valueOf(scanner.nextLine());
    };
    public Client()  {
        frame = new MainFrame();
    }
    public static void init() {
        instance = new Client();
        instance.initInstance();
    }

    public static Sender getSender() {
        return instance;
    }

    private void initInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        System.out.println("client created");
        Login.setInstance(this::login);
        if (passDate != null) {
            login(passDate);
        } else {
            frame.setContentPanel(Login.getInstance());//this::login));
        }
    }

    public static Client getInstance() {
        return instance;
    }

    public LoginResult login(Identifier date) {
        LoginResult result = tryToConnectServer(date);
        if (result == LoginResult.PASS) {
            Logger.loginUser(date.getUserID());
            authToken = scanner.nextLine();
            enterMainPage(scanner.nextLine(), date);
            startListeningToServer();
            System.out.println(authToken);
        }
        return result;

 /*
        frame = new MainFrame(this);

        client = new Client(socket.getInputStream(), this, frame);
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String a = scanner.nextLine();

                if (a.equals("m")) {
                    System.out.println("Enter number of player: ");
                } else if (a.equals("n")) {
                } else if (equals("p")) {
                    sendEmojiMessage(String.valueOf(TypeTask.PLAY));
                } else {
                    sendEmojiMessage(a);
                }
            }
        } catch (Exception w) {
            System.out.println("cant receive client from server");
        }
        return null;*/
    }

    private void startListeningToServer() {
        responseHandler = new ResponseHandler(in);
        responseHandler.start();
    }

    private LoginResult tryToConnectServer(Identifier date) {
        try {
            LoginResult result = connectSocket.call(); // have exception

            if (result == LoginResult.DISCONNECTED) return result;
            sendObject(date);
            return tryCatchResponseFromServer();
        } catch (Exception e) {
            return LoginResult.DISCONNECTED;
        }
    }

    private void enterMainPage(String constructorData, Identifier date) {
        passDate = date;
        frame.enterMainPage(gson.fromJson(constructorData, ConstructorData.class),
                passDate.getUserID());
    }

    private void sendObject(Object date) {
        out.println(gson.toJson(date));
        out.flush();
    }

    private LoginResult tryCatchResponseFromServer() {
        FutureTask<LoginResult> task = new FutureTask<>(catchLoginResult);
        try {
        //    task.get();
            task.run();
            return task.get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            task.cancel(true);
            return LoginResult.DISCONNECTED;
        }
    }

    @Override
    public synchronized void send(RequestType type, Object... args) {
        out.println(authToken);
        if (args.length == 0) {
            sendObject(new Request(type));
        } else {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                data.add((args[i] instanceof String ? (String) args[i] : gson.toJson(args[i])));
            }
            sendObject(new Request(type, data));
        }
    }

    public void autoLogin() {
        login(passDate);
    }
}
