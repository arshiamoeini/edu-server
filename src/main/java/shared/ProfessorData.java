package shared;

import GUI.RowPanel;
import database.Professor;

public class ProfessorData {
    private long id;
    private String name;
    private String facultyName;
    private String email;
    private MasterLevel masterLevel;
    private long phoneNumber;
    private long roomNumber;

    public ProfessorData(RowPanel professorRow) {
        id = Long.parseLong(professorRow.getText(2));
        facultyName = professorRow.getText(3);
        name = professorRow.getText(4);
        email = professorRow.getText(5);
        masterLevel = MasterLevel.valueOf(professorRow.getText(6));
        phoneNumber = Long.parseLong(professorRow.getText(7));
        roomNumber = Long.parseLong(professorRow.getText(8));
    }

    public ProfessorData(Professor professor) {
        id = professor.getId();
        name = professor.getName();
        facultyName = professor.getFaculty().getName();
        email = professor.getEmail();
        masterLevel = professor.getMasterLevel();
        phoneNumber = professor.getPhoneNumber();
        roomNumber = professor.getRoomNumber();
    }

    public long getId() {
        return id;
    }

    public Object[] getInRow() {
        return new Object[]{facultyName, name, email, masterLevel, phoneNumber, roomNumber};
    }

    public String getName() {
        return name;
    }
}
