package server;

import database.*;

import java.util.Collection;
import java.util.stream.Collectors;

public class  ServerMain {
    public static void main(String[] args) {
        Database.getInstance().save();
       // Student student = (Student) Database.getInstance().getUser(1234);
       // System.out.println(student.getFaculty().getName());
       // Course course = Database.getInstance().getCourse(2005);
       // System.out.println(course.getName());
    //    CourseView courseView = Database.getInstance().getCourseView(20051003L);
     //   System.out.println(courseView.getName());
     //   Database.getInstance().closeSession();
    //    EducationalAssistant educationalAssistant = (EducationalAssistant) Database.getInstance().getUser(1003);
  //      System.out.println(course.getPrerequisite().size()); //stream().map(x -> x.getName()).collect(Collectors.joining(" ")));
  //      User user = Database.getInstance().getUser(1003);
   //     System.out.println(user.getNotifications().get(1).getTitle());
    //    Database.getInstance().closeSession();
               Server server = new Server();
           server.start();
    }
}
