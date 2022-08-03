package client;

import GUI.*;
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
    private Identifier passDate;
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
        Scanner scanner = new Scanner(in);
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
        frame.setContentPanel(Login.getInstance());//this::login));
    }

    public static Client getInstance() {
        return instance;
    }

    public LoginResult login(Identifier date) {
        LoginResult result = tryToConnectServer(date);
        if (result == LoginResult.PASS) enterMainPage(date);
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

    private void enterMainPage(Identifier date) {
        Scanner scanner = new Scanner(in);
        authToken = scanner.nextLine();
        frame.enterMainPage(gson.fromJson(scanner.nextLine(), ConstructorData.class));
        System.out.println(authToken);
        passDate = date;
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
    public synchronized void send(Object... args) {
        if (args.length == 1) {
            sendObject(new Request((RequestType) args[0]));
        } else {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                data.add(gson.toJson(args[i]));
            }
            sendObject(new Request((RequestType) args[0], data));
        }
    }
}
