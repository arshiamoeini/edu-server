package GUI.RECORD_AFFAIRS;

import GUI.CellPane;
import GUI.DemoList;
import MODELS.Classroom;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class recordsList extends DemoList {
    public recordsList(ArrayList<Classroom.classRating> args, boolean editable) {
        super();
        columnsTitle = new String[] {"student name", "student ID", "scour", "protest", "register answer", "answer"};
        designTopics();
        for (Classroom.classRating rating: args) {
            addRowWithProtest(rating, editable);
        }
    }

    private void addRowWithProtest(Classroom.classRating rating, boolean editable) {
        gbcFiller.gridy = rowsCounter++;
        columnCounter = 0;
        addCopyableTextInRow(rating.getStudentName());
        addCopyableTextInRow(String.valueOf(rating.getStudentID()));
        CellPane score = addCopyableTextInRowForEdit(String.valueOf(rating.getScore()));
        if (editable) score.setEditable();
        addCopyableTextInRow(rating.getProtest());
        JButton okButton = new JButton("register");
        CellPane ok = new CellPane(okButton);
        gbcFiller.gridx = columnCounter++;
        pane.add(ok , gbcFiller);
        CellPane text = addCopyableTextInRowForEdit("                ");
        if (editable) text.setEditable();

        if(editable) okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rating.setScore(Double.valueOf(score.getText()));
                    rating.setAnswer(text.getText());
                    ok.setButton(new JButton("Done"));
                } catch (Exception error) {
                    System.out.println("not valid score");
                }

            }
        });
    }
}
