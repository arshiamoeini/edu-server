package GUI;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SubjectsListTemp extends DemoList {

//    int[] padeSizeInRow = {};

    public SubjectsListTemp() {
        super();
        //setting
    //    setSize(new Dimension(MainFrame.MAIN_WIDTH - 100, MainFrame.MAIN_HEIGHT - 100));
    //    setBackground(Color.RED);
    //    setOpaque(true);

        //set topics
        columnsTitle = new String[]{
                "course id",
                "credit",
                "course name",
                "<html>prerequisite and<br>co-requisite</html>",
                "capacity",
                "registration number",
                "professor name",
                "exam date",
        };
        designTopics();
    }

    @Override
    public void addSubjectRow(int id, int credit, String name, ArrayList<Integer> prerequisite, ArrayList<Integer> coRequisite, int capacity, int registrationNumber, String professorName, LocalDateTime examDate) {
        gbcFiller.gridy = (rowsCounter++);
        columnCounter = 0;
        super.addSubjectRow(id, credit, name, prerequisite, coRequisite, capacity, registrationNumber, professorName, examDate);
    }
}