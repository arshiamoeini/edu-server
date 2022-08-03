package database;

import MODELS.CourseTemp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import shared.ConstructorData;
import shared.Identifier;
import shared.LoginResult;
import shared.UserType;

import java.util.function.Consumer;

public class Database {
    private static Database instance;
    private static SessionFactory sessionFactory = buildSessionFactory();

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
        Student student = (new Student(1234, "1234", faculty));
        EducationalAssistant educationalAssistant = new EducationalAssistant(1003, "hello", faculty);
        faculty.addStudent(student);
        faculty.addProfessor(educationalAssistant);
        session.save(faculty);
        session.save(student);
        session.save(educationalAssistant);
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
                result = new ConstructorData(getUserType(user), getFactories(), getPrograms());
            } else {
                result = LoginResult.WRONG_PASSWORD;
            }
        }
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

    public User getUser(long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }
    private String[] getPrograms() {
        String[] values = new String[CourseTemp.Program.values().length];
        for (int i = 0; i < CourseTemp.Program.values().length; i++) {
            values[i] = CourseTemp.Program.values()[i].toString();
        }
        return values;
    }

    private String[] getFactories() {
        return new String[0];
    }
}
