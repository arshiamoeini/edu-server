package server;

import database.Database;
import database.Student;
import database.User;

public class  ServerMain {
    public static void main(String[] args) {
        Database.getInstance().save();
        Student student = (Student) Database.getInstance().getUser(1234);
        System.out.println(student.getFaculty().getName());
    //   Server server = new Server();
    //   server.start();
    }
}
