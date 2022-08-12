package DATE_BASE;

import MODELS.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.UserTemp;
import shared.Program;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InitialData {
    public static String SOURCE = "src/main/resources";
    public static String FACULTIES_SOURCE = SOURCE + "/faculties.json";
    public static String UNIVERSITY_SOURCE = SOURCE + "/university.json";
    public static Gson GSON;
    public InitialData() {
        /*
        try {


            file = new File(FACULTIES_SOURCE);
            reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
            Faculty[] faculties = GSON.fromJson(reader, Faculty[].class);

            Faculties.setInstance(new Faculties(new ArrayList<>(Arrays.asList(faculties))));

            for (Faculty faculty: faculties) {
                faculty.getFacultyTag().setFaculty(faculty);
            }
            for (User user: users) {
                addUserToFaculties(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(UserTemp.class , new UserAdapter());
            builder.setPrettyPrinting();
            GSON = builder.create();
        }
        File file = new File(UNIVERSITY_SOURCE);
     /*   try {
            FileOutputStream fos = new FileOutputStream(FACULTIES_SOURCE);
            OutputStreamWriter isr = new OutputStreamWriter(fos,
                    StandardCharsets.UTF_8);

            GSON.toJson(new User(100, "123"), isr);

            isr.close();


            //User[] users = GSON.fromJson(reader, User[].class);
            Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

            User user = GSON.fromJson(reader, User.class);
            System.out.println(user instanceof Student);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        ArrayList<FacultyTemp> faculties = new ArrayList<>();

        FacultyTemp faculty1 = new FacultyTemp("math", 0);
        FacultyTemp faculty2 = new FacultyTemp("css", 1);
        FacultyTemp faculty3 = new FacultyTemp("cee", 2);
        FacultyTemp faculty4 = new FacultyTemp("phisice", 3);
        FacultyTemp faculty5 = new FacultyTemp("chemi", 4);
        faculties.add(faculty1);
        faculties.add(faculty2);
        faculties.add(faculty3);
        faculties.add(faculty4);
        faculties.add(faculty5);
        University.setInstance(new University(faculties));

        faculty1.addProfessor(new CampusChairmenTemp(1111, "foo") {
            {
                setName("boss");
            }
        });
        faculty1.addProfessor(new EducationalAssistantTemp(111, "goo"){
            {
                setName("ali");
                //               addRecommendation(12);
            }
        });
        faculty2.addProfessor(new CampusChairmenTemp(1112, "foo") {
            {
                setName("boss");
            }
        });
        faculty2.addProfessor(new EducationalAssistantTemp(112, "goo"){
            {
                setName("ali");
                //               addRecommendation(12);
            }
        });
        faculty3.addProfessor(new CampusChairmenTemp(1113, "foo") {
            {
                setName("boss");
            }
        });
        faculty3.addProfessor(new EducationalAssistantTemp(113, "goo"){
            {
                setName("ali");
                //               addRecommendation(12);
            }
        });
        faculty4.addProfessor(new CampusChairmenTemp(1114, "foo") {
            {
                setName("boss");
            }
        });
        faculty4.addProfessor(new EducationalAssistantTemp(114, "goo"){
            {
                setName("ali");
                //               addRecommendation(12);
            }
        });
        faculty5.addProfessor(new CampusChairmenTemp(1115, "foo") {
            {
                setName("boss");
            }
        });
        faculty5.addProfessor(new EducationalAssistantTemp(115, "goo"){
            {
                setName("ali");
                //               addRecommendation(12);
            }
        });

        ArrayList<StudentTemp> students = new ArrayList<>();
        faculty1.addStudent(new BachelorStudentTemp(1234, "some typing"));
        faculty1.addStudent(new GraduateStudentTemp(123, "try"));
        faculty1.addStudent(new BachelorStudentTemp(12, "12341"));
        faculty1.addStudent(new GraduateStudentTemp(1, "1234"));
        faculty1.addStudent(new PhDStudentTemp(12345, "1234"));
        faculty1.addStudent(new PhDStudentTemp(91, "1"));
        /*
        ((BachelorStudent) faculty1.getUser(12)).addAnsweredRecommendation("salam");
        ((BachelorStudent) faculty1.getUser(12)).addAnsweredRecommendation("khobi");

        ArrayList<Professor> professors = new ArrayList<>();
        faculty1.addEducationalRequest(Faculty.RequestType.MAJOR, "math", 12);
        */

        faculty1.addCourses("riazi 1", 2004, new ArrayList<CourseTemp>(), new ArrayList<>(), Program.UNDERGRADUATE);
        ArrayList<CourseTemp> pre = new ArrayList<>();
        pre.add(faculty1.getCourses().get(0));
        faculty1.addCourses("riazi 2", 2005, pre, new ArrayList<>(), Program.UNDERGRADUATE);

        ArrayList<LocalDateTime> times = new ArrayList<>();
        times.add(LocalDateTime.of(1401, 1, 1, 12, 30));
        faculty1.addClassrooms(new ClassroomTemp(1, 1, 12, LocalDateTime.of(1400, 12, 1, 12, 30), times));
        ClassroomTemp classroom = new ClassroomTemp(0, 1, 12, LocalDateTime.of(1400, 12, 2, 12, 30), times);
        faculty1.addClassrooms(classroom);

        classroom.addStudent(12);
        classroom.addStudent(1234);
     //   University.getInstance().getUser(111).addClass(1);
      //  University.getInstance().getUser(111).addClass(0);
    }

    public void addUserToFaculties(UserTemp user) {
        //the faculty student and professor field are retake
        if (user instanceof StudentTemp) {
        } else if (user instanceof CampusChairmenTemp) {
            user.getFaculty().setCampusChairmen((CampusChairmenTemp) user);
        } else if (user instanceof EducationalAssistantTemp) {
            user.getFaculty().setEducationalAssistant((EducationalAssistantTemp) user);
        } else if (user instanceof ProfessorTemp){
            user.getFaculty().addProfessor((ProfessorTemp) user);
        } else {
            System.out.println("break");
        }
    }

    public static void save() {
        try {
       /*     FileOutputStream fos;
            OutputStreamWriter isr = new OutputStreamWriter(fos,
                    StandardCharsets.UTF_8);

            isr.close();

            fos = new FileOutputStream(FACULTIES_SOURCE);
            isr = new OutputStreamWriter(fos,
                    StandardCharsets.UTF_8);
          //  Faculties.getInstance().deleteUsersOfFaculty();
           // GSON.toJson(Faculties.getInstance().getFaculties(), isr);

            isr.close();*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
