package GUI.RECORD_AFFAIRS;

import GUI.CellPane;
import GUI.DemoList;
import GUI.OptionCentricText;
import GUI.PanelDesigner;
import LOGIC.Command;
import LOGIC.Logger;
import MODELS.ClassroomTemp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordScores implements PanelDesigner {
    private JTabbedPane tabbedPane1;
    private JPanel panel;
    private JPanel toRecord;
    private JButton show;
    private JPanel selectClass;
    private JPanel register;
    private JPanel list;

    public RecordScores() {
        OptionCentricText selected = new OptionCentricText(Command.getInstance().getClassrooms());
        selectClass.add(selected);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showList(selected.getSelectedIndex());
            }
        });
    }

    private void showList(int selectedClassIndex) {
        //reset list
        list.removeAll();
        register.removeAll();

        ClassroomTemp classroom = Command.getInstance().getClassrooms().get(selectedClassIndex);

        JButton registerButton = new JButton();
        if (classroom.isRegistered()) {
            registerButton.setText("Final");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    classroom.setAFinal();
                    showList(selectedClassIndex);
                    toRecord.revalidate();
                }
            });
            list.add(new recordsList(classroom.getRatings(), true));
        } else if (classroom.isaFinal()) {//
            registerButton.setText("Done");
            // TODO
        } else {
            registerButton.setText("register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (classroom.setRegistered()) {
                        showList(selectedClassIndex);
                        toRecord.revalidate();
                    }
                }
            });
            addSimpleDemo(classroom);
        }
        register.add(registerButton);
    }
    private void addSimpleDemo(ClassroomTemp classroom){
        toRecord.add( new DemoList() {
            {
                columnsTitle = new String[] {"student name", "student id", "register", "score"};
                designTopics();
                for (ClassroomTemp.classRating rating : classroom.getRatings()) {
                    addRowSimpleScore(rating);
                }
            }
            private void addRowSimpleScore(ClassroomTemp.classRating rating) {
                gbcFiller.gridy = rowsCounter++;
                columnCounter = 0;
                addCopyableTextInRow(rating.getStudentName());
                addCopyableTextInRow(String.valueOf(rating.getStudentID()));
                JButton okButton = new JButton("register");
                CellPane ok = new CellPane(okButton);
                gbcFiller.gridx = columnCounter++;
                pane.add(ok , gbcFiller);
                CellPane score = addCopyableTextInRowForEdit(String.valueOf(rating.getScore()));
                score.setEditable();

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            double scoreNumber = Double.valueOf(score.getText());
                            if (0 <= scoreNumber && scoreNumber <= 20) {
                                rating.setScore(scoreNumber);
                            } else {
                                throw new RuntimeException();
                            }
                        } catch (Exception error) {
                            System.out.println("not valid score");
                            Logger.logInfo((this.toString()) + "try to score with not valid value");
                        }
                    }
                });
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
