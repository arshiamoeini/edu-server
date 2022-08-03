package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.Database;
import shared.Identifier;
import shared.LoginResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8000; //TODO Config
    private ArrayList<Socket> sockets = new ArrayList<>();
    private ArrayList<PrintWriter> clientOuts = new ArrayList<>();
    private ExecutorService listeners = Executors.newFixedThreadPool(10); //TODO config

    private Gson gson;

    /*
    public Server(ArrayList<ClientHandler> clientHandlers) {
        this.games = clientHandlers;
        status = ServerStatus.WAITING;
    }*/

    public Server() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);

                InputStream in = socket.getInputStream();
                Scanner scanner = new Scanner(in);
                PrintWriter writer = new PrintWriter(socket.getOutputStream());

                listeners.execute(() -> {
                    System.out.println("server : " + socket);

                    try {
                        Identifier indent = gson.fromJson(scanner.next(), Identifier.class);

                        Database.getInstance().signIn(indent, x -> login(in, writer, x));
                    } catch (com.google.gson.JsonSyntaxException e) {
                        System.out.println("invalid input");
                    } catch (RuntimeException e) {
                        System.out.println("connection fail");
                    }
                    System.out.println("first connection end ");
                });
            }
        } catch (IOException e) {
            System.out.println("socket disconnected");
            start();
        }
    }
    private void login(InputStream in, PrintWriter writer, Object result) {
        if (result instanceof LoginResult) {
            writer.println(result);
            writer.flush();
        } else {
            writer.println(LoginResult.PASS);
            writer.flush();
            try {
                listeners.execute(new Listener(in, x -> identification(writer, x)));
                writer.println(gson.toJson(result));
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };
    public int identification(PrintWriter writer, String authToken) {
        writer.println(authToken);
        writer.flush();
        return addClientOut(writer);
    }

    public synchronized int addClientOut(PrintWriter writer) {
        clientOuts.add(writer);
        return clientOuts.size() - 1;
    }

  /*  private boolean is(int idOfGame) {
        for (Game game : games)
            if (game.getId() == idOfGame)
                return true;
        return false;
    }

    /*

    public void startGame() throws InterruptedException {
        System.out.println("Game is started!");
        status = ServerStatus.STARTED;
        numberOfPlayers = clientHandlers.get(0).number;

        game.Start();
        game.print();
    }*/

}