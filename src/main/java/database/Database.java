package database;

import shared.Program;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import shared.*;

import java.util.*;
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
        Faculty faculty = new Faculty("math");
        EducationalAssistant educationalAssistant = new EducationalAssistant(1003, "hello", faculty);
        faculty.setAssistant(educationalAssistant);

        BachelorStudent student = (new BachelorStudent(1234, "1234", faculty));
        faculty.addStudent(student);
        faculty.addProfessor(educationalAssistant);

        Course course1 = new Course(2004, faculty, "riazi 1", 4, new HashSet<>(), new HashSet<>());
        Course course2 = new Course(2005, faculty, "riazi 2", 4, new HashSet<>(Collections.singleton(course1)), new HashSet<>());
        faculty.addCourse(course1);
        faculty.addCourse(course2);
        Classroom classroom = new Classroom(20051003L, faculty, course2, educationalAssistant, Program.UNDERGRADUATE);
        Classroom classroom1 = new Classroom(20041003L, faculty, course1, educationalAssistant, Program.COMMON);
        Classroom defaultClassroom = new Classroom(1L, faculty, null, null, Program.UNDERGRADUATE);
        faculty.addClassroom(classroom);
        faculty.addClassroom(classroom1);
        faculty.addClassroom(defaultClassroom);
        educationalAssistant.addClassroom(classroom);
        educationalAssistant.addClassroom(classroom1);
        CourseView courseView = new CourseView(classroom);
        CourseView courseView1 = new CourseView(classroom1);
        educationalAssistant.addCourseView(courseView);
        educationalAssistant.addCourseView(courseView1);
        CourseViewRegistration registration = courseView.addStudent(student, false);
        ClassroomRating classroomRating = classroom1.addStudent(student);
        classroomRating.setScore(12);

        Notification notif1 = new Notification(NotificationType.INFO, "salam", student.getId(),
                "khobi", student.getName());
        Notification notif2 = new Notification(NotificationType.INFO, "pase", student.getId(),
                "dotabashe", student.getName());

        educationalAssistant.addNotification(notif2);
        educationalAssistant.addNotification(notif1);

        session.save(faculty);
        session.save(student);
        session.save(educationalAssistant);

        session.save(course1);
        session.save(course2);

        session.save(classroom);
        session.save(classroom1);
        session.save(defaultClassroom);
        session.save(classroomRating);
        session.save(courseView);
        session.save(courseView1);
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
        Object result = getLoginResult(user, indent);

        closeSession();
        sendToClient.accept(result);
    }
    public Object getLoginResult(User user, Identifier indent) {
        if (user == null) return LoginResult.WRONG_USER_ID;
        if (user.getHashOfPassword() == indent.getHashOfPassword()) {
            return new ConstructorData(getUserType(user), getFactoriesName(),
                    getPrograms(), getFaculty(user).getName());
        }
        return LoginResult.WRONG_PASSWORD;
    }

    public Faculty getFaculty(User user) {
        if (user instanceof Student) {
            return ((Student) user).getFaculty();
        } else if (user instanceof Professor) {
            return ((Professor) user).getFaculty();
        }
        return null;
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
    public Professor getProfessor(long id) {
        if (session == null) session = sessionFactory.openSession();
        return session.get(Professor.class, id);
    }
    private String[] getPrograms() {
        String[] values = new String[Program.values().length];
        for (int i = 0; i < Program.values().length; i++) {
            values[i] = Program.values()[i].toString();
        }
        return values;
    }

    private String[] getFactoriesName() {
        if(session == null) session = sessionFactory.openSession();
        String hql = "FROM Faculty f ORDER BY f.id ASC";
        return session.createQuery(hql, Faculty.class).
                getResultList().stream().
                map(x -> x.getName()).
                toArray(String[]::new);
    }
    public List<Faculty> getFaculties() {
        if(session == null) session = sessionFactory.openSession();
        String hql = "FROM Faculty f ORDER BY f.id ASC";
        return session.createQuery(hql, Faculty.class).
                getResultList();
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
//        System.out.println(classroom.getTeacher().getId());
        session.close();
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
            return ((Student) user).getClassroomRates().stream()
                    .map(x -> x.getClassroom())
                    .collect(Collectors.toSet());
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
     /*   String hql = "FROM Faculty f ORDER BY f.id ASC";
        ArrayList<Faculty> faculties = new ArrayList<>(session.createQuery(hql, Faculty.class).
                getResultList().stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList()));

        return faculties.get(0);
      */
        return session.find(Faculty.class, name);
    }

    public void addNotification(User user, Notification notification) {
        session.beginTransaction();
        User suser = session.find(User.class, user.getId());
        suser.addNotification(notification);
        session.update(suser);
        session.save(notification);
        session.getTransaction().commit();
    }

    public void addMajorRequest(BachelorStudent student, MajorRequest majorRequest) {
        session.beginTransaction();
        BachelorStudent superStudent = session.find(BachelorStudent.class, student.getId());
        superStudent.addMajorRequest(majorRequest);
        session.update(superStudent);
        session.save(majorRequest);
        session.getTransaction().commit();
    }

    public List<Professor> getProfessors(Faculty faculty) {
        if (session == null) session = sessionFactory.openSession();
        Faculty superFaculty = session.find(Faculty.class, faculty.getName());
        return superFaculty.getProfessors();
    }
    public Set<Classroom> getClassrooms(Faculty faculty) {
        if (session == null) session = sessionFactory.openSession();
        Faculty superFaculty = session.find(Faculty.class, faculty.getName());
        return superFaculty.getClassrooms();
    }

    public void addDefaultClassroom(String facultyName, String programStr) {
        session = sessionFactory.openSession();
        if (getProfessor(1L) != null) return;
        session.beginTransaction();
        Faculty faculty =  session.find(Faculty.class, facultyName);
        Classroom classroom = getDefaultClassroom(faculty, Program.valueOf(programStr));
        faculty.addClassroom(classroom);
        session.update(faculty);
        session.save(classroom);
        session.getTransaction().commit();
    }

    private Classroom getDefaultClassroom(Faculty faculty, Program program) {
        return new Classroom(1L,
                faculty,
                null,
                null,
                program);
    }

    public int addStudent(Faculty faculty, String[] data) {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        try {
            addStudent(faculty, new Student(faculty, data));
            return 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            return -1;
        }
    }

    private void addStudent(Faculty faculty, Student student) {
        session.beginTransaction();
        faculty.addStudent(student);
        session.update(faculty);
        session.save(student);
        session.getTransaction().commit();
    }

    public Set<ClassroomRating> getRegisteredCourses(Student student) {
        if (session == null) session = sessionFactory.openSession();
        Student superStudent = session.find(Student.class, student.getId());
        return superStudent.getClassroomRates();
    }

    public ClassroomRating getRating(Long id) {
        if(session == null) session = sessionFactory.openSession();
        ClassroomRating rating = session.get(ClassroomRating.class, id);
    //    System.out.println(classroom.getTeacher().getId());
        // System.out.println(classroom.getProfessor1().getId());//getClassrooms().stream().map(Classroom::getId).collect(Collectors.toList()));
        return rating;
    }

    public void updateRating(ClassroomRating rating, double score, String protest, String answer) {
        if (session == null) session = sessionFactory.openSession();
        session.beginTransaction();
        if (score != -1) rating.setScore(score);
        if (protest != null) rating.setProtest(protest);
        if (answer != null) rating.setAnswer(answer);
        session.update(rating);
        session.getTransaction().commit();
    }

    public Object getClassrooms(Professor professor) {
        if (session == null) session = sessionFactory.openSession();
        Professor superProfessor = session.find(Professor.class, professor.getId());
        return superProfessor.getClassrooms();
    }

    public Set<ClassroomRating> getClassroomRatings(Classroom classroom) {
        if (session == null) session = sessionFactory.openSession();
        Classroom superClassroom = session.find(Classroom.class, classroom.getId());
        return superClassroom.getStudentsRating();
    }

    public void updateClassroom(Classroom classroom, Boolean registered, Boolean goFinal) {
        if (session == null) session = sessionFactory.openSession();
        session.beginTransaction();
        if (registered != null) classroom.setRegistered(registered);
        if (goFinal != null) classroom.setaFinal(goFinal);
        session.update(classroom);
        session.getTransaction().commit();
    }

    public void setTimeToTakingClasses(Faculty faculty, boolean timeToTakingClasses) {
        if (session == null) session = sessionFactory.openSession();
        session.beginTransaction();
        faculty.setTimeToTakingClasses(timeToTakingClasses);
        session.update(faculty);
        session.getTransaction().commit();
    }
}
