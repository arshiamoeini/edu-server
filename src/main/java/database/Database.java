package database;

import MODELS.CourseTemp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import shared.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Database {
    private static Database instance;
    private static SessionFactory sessionFactory = buildSessionFactory();
    private Session session;
    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure()
                .build();
        SessionFactory sessionFactory1 = new MetadataSources( registry).buildMetadata()
                .buildSessionFactory();
        return sessionFactory1;
    }

    static  {
        instance = new Database();
    }
    public Database() {
    }
    public void save() {
        System.out.println(getPrograms());
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Faculty faculty = new Faculty(108, "math");
        EducationalAssistant educationalAssistant = new EducationalAssistant(1003, "hello", faculty);
        faculty.setAssistant(educationalAssistant);

        Student student = (new Student(1234, "1234", faculty));
        faculty.addStudent(student);
        faculty.addProfessor(educationalAssistant);

        Course course1 = new Course(2004, faculty, "riazi 1", 4, new HashSet<>(), new HashSet<>());
        Course course2 = new Course(2005, faculty, "riazi 2", 4, new HashSet<>(Collections.singleton(course1)), new HashSet<>());
        faculty.addCourse(course1);
        faculty.addCourse(course2);
        Classroom classroom = new Classroom(20051003L, faculty, course2, educationalAssistant);
        faculty.addClassroom(classroom);
        educationalAssistant.addClassroom(classroom);
        CourseView courseView = new CourseView(classroom);
        educationalAssistant.addCourseView(courseView);
        CourseViewRegistration registration = courseView.addStudent(student, false);

        Notification notif1 = new Notification(NotificationType.INFO, "salam", student.getId(),
                "khobi", student.getName(), educationalAssistant);
        Notification notif2 = new Notification(NotificationType.INFO, "pase", student.getId(),
                "dotabashe", student.getName(), educationalAssistant);

        educationalAssistant.addNotification(notif2);
        educationalAssistant.addNotification(notif1);

        session.save(faculty);
        session.save(student);
        session.save(educationalAssistant);

        session.save(course1);
        session.save(course2);

        session.save(classroom);
        session.save(courseView);
        session.save(registration);

        session.save(notif1);
        session.save(notif2);

        session.getTransaction().commit();
        session.close();
    }

    public static Database getInstance() {
        return instance;
    }

    public synchronized void signIn(Identifier indent, Consumer<Object> sendToClient) { //TODO ability to responds to multi user login
        User user = getUser(indent.getUserID());
        Object result;
        if (user == null) {
            result = LoginResult.WRONG_USER_ID;
        } else {
            if (user.getHashOfPassword() == indent.getHashOfPassword()) {
                result = new ConstructorData(getUserType(user), getFactoriesName(), getPrograms());
            } else {
                result = LoginResult.WRONG_PASSWORD;
            }
        }
        closeSession();
        sendToClient.accept(result);
    }

    private UserType getUserType(User user) {
        if (user instanceof Student) {
            if (user instanceof BachelorStudent) return UserType.BachelorStudent;
            if (user instanceof GraduateStudent) return UserType.GraduateStudent;
            return UserType.PHDStudent;
        } else if (user instanceof Professor) {
            if (user instanceof EducationalAssistant) return UserType.EducationalAssistant;
            return UserType.CampusChairmen;
        }
        return UserType.MrMohseni;
    }

    public User getUser(long id) { //TODO generic
        session = sessionFactory.openSession();
        User user = session.get(User.class, id);
      //  Set<Classroom> classrooms = (session.get(Professor.class, id)).getClassrooms();
       // session.close();
        return user;
    }
    public Student getStudent(long id) {
        session = sessionFactory.openSession();
        Student student = session.get(Student.class, id);
        return student;
    }
    private String[] getPrograms() {
        String[] values = new String[CourseTemp.Program.values().length];
        for (int i = 0; i < CourseTemp.Program.values().length; i++) {
            values[i] = CourseTemp.Program.values()[i].toString();
        }
        return values;
    }

    private String[] getFactoriesName() {
        if(session == null) session = sessionFactory.openSession();
        String hql = "FROM Faculty f ordered BY f.id ASC";
        return session.createQuery(hql, Faculty.class).
                getResultList().stream().
                map(x -> x.getName()).
                toArray(String[]::new);
    }

    public Course getCourse(int id) {
        Session session = sessionFactory.openSession();
        Course course = session.get(Course.class, id);
  //      System.out.println(course.getPrerequisite().stream().map(x -> x.getName()).collect(Collectors.joining(" ")));
  //      session.close();
        return course;
    }

    public Classroom getClassroom(long id) {
        Session session = sessionFactory.openSession();
        Classroom classroom = session.get(Classroom.class, id);
        System.out.println(classroom.getTeacher().getId());
       // System.out.println(classroom.getProfessor1().getId());//getClassrooms().stream().map(Classroom::getId).collect(Collectors.toList()));
        return classroom;
    }
    public void closeSession() {
        if (session != null) session.close();
        session = null;
    }

    public Set<Classroom> getWeeklyClassrooms(long id) {
        User user = getUser(id);
        if (user instanceof Professor) {
            return ((Professor) user).getClassrooms();
        } else {
            return null;
        }
    }

    public CourseView getCourseView(long id) {
        session = sessionFactory.openSession();
        CourseView courseView = session.get(CourseView.class, id);
        return courseView;
    }

    public Set<CourseView> getCourseViews(long userId) {
        User user = getUser(userId);
        if (user instanceof Professor) {
            return ((Professor) user).getCourseViews();
        } else {
            return ((Student) user).getRegisteredCourse().stream().
                    map(x -> x.getCourseView()).
                    collect(Collectors.toSet());
        }
    }

    public List<Notification> getNotifications(long userId) {
        User user = getUser(userId);
        return user.getNotifications();
    }

    public Faculty getFacultyByName(String name) {
        if (session == null) session = sessionFactory.openSession();
        Query query= session.createQuery("from Faculty where name=:name");
        query.setParameter("name", name);
        return (Faculty) query.uniqueResult();
    }

    public void addNotification(User user, Notification notification) {
        user.addNotification(notification);
        session.update(user);
        session.save(notification);
        session.getTransaction().commit();
    }
}
