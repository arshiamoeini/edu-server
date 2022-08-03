package MODELS;

import database.UserTemp;

import java.util.ArrayList;

public class University {
    private static University instance;

    private ArrayList<FacultyTemp> faculties;

    public static void setInstance(University university) {
        instance = university;
    }
    public static University getInstance() {
        return instance;
    }

    public University(ArrayList<FacultyTemp> faculties) {
        this.faculties = faculties;
    }


    public UserTemp getUser(long userID) {
        UserTemp user;
        for (FacultyTemp faculty: faculties) {
            user = faculty.getUser(userID);
            if (user != null) {
                return user;
            }
        }
        return null;
    }
    public FacultyTemp getFaculty(int id) {
        return faculties.get(id);
    }

    public Object[] getFacultiesName() {
        return faculties.stream().map(FacultyTemp::getMassage).toArray();
    }
}
