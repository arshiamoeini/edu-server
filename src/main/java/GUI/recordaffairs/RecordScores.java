package GUI.recordaffairs;

import GUI.*;
import LOGIC.Command;
import LOGIC.Logger;
import MODELS.ClassroomTemp;
import com.google.gson.Gson;
import shared.ClassroomNameData;
import shared.RequestType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RecordScores extends DemoSet implements SpecialUserPage {
    private JPanel panel;
    private JPanel toRecord;
    private JButton show;
    private JPanel selectClass;
    private JPanel register;
    private JPanel list;
    private JTabbedPane tabbedPane1;
    private JPanel titles;
    private JPanel recordListView;
    private RecordsList recordsList;
  //  private String[] classroomsToSelect;
  //  private Long[] idOfProfessor;
   // private OptionCentricText classroomsSelector;

    public RecordScores() {
        designTopics();
        list.add(getListPanel());
       /* show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showList(selected.getSelectedIndex());
            }
        });*/
    }

    @Override
    public JPanel getTitles() {
        return titles;
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

    @Override
    public void setExit() {
        ManePagePanelFactory.setOutButtonToExitToMainPage();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.GET_CLASSROOMS_FOR_SCORE_SHOW;
    }

    @Override
    public synchronized void update(ArrayList<String> data, Gson gson) throws Exception {
        Set<ClassroomNameData> classrooms = data.stream().
                map(x -> gson.fromJson(x, ClassroomNameData.class)).
                collect(Collectors.toSet());
        removeClassrooms(classrooms);
        updateClassrooms(classrooms);
        panel.repaint();
        panel.revalidate();
    }

    private void updateClassrooms(Set<ClassroomNameData> classrooms) {
        for (ClassroomNameData classroom: classrooms) {
            if (!this.contain(classroom.getId())) {
                addNewClassroom(classroom);
            }
        }
    }

    private void addNewClassroom(ClassroomNameData classroom) {
        RowPanel rowPanel = new RowPanel();
        rowPanel.addCopyableDataInRow(classroom.getCourseName(), classroom.getTeacherName());
        rowPanel.addComponents(getEnterButtonToClassroomScoresPanel(classroom.getId(), classroom.isEditScore(), classroom.isAnswerStudent()));
        addRow(classroom.getId(), rowPanel);
    }

    private JButton getEnterButtonToClassroomScoresPanel(long id, boolean editScore, boolean answerStudent) {
        return new JButton("enter") {
            {
                addActionListener(e -> {
                    recordsList = answerStudent ? new answerRecordList(id, editScore, () -> exitToThisPage())
                            : new RecordsList(id, editScore,() -> exitToThisPage());
                    addList(recordsList.getListPanel(), recordsList.getPanel());
                    recordsList.enterPage();
                    enterToSubPanel(0);
                    recordListView.repaint();
                    recordListView.revalidate();
                });
            }

            private void addList(JPanel list, JPanel button) {
                recordListView.removeAll();
                recordListView.add(list);
                recordListView.add(button, BorderLayout.NORTH);
            }
        };
    }

    private void removeClassrooms(Set<ClassroomNameData> classrooms) {
        Set<Long> ratesDataSet = new TreeSet<>(classrooms.stream().map(x -> x.getId()).collect(Collectors.toSet()));
        Set<Long> oldRates = this.getRowsId().stream()
                .filter(x -> x != 0 && !ratesDataSet.contains(x)).collect(Collectors.toSet());
        oldRates.forEach(x -> this.removeRow(x));
    }

    @Override
    public void enterToSubPanel(int id) {
        UserConstantInformation.getInstance().setOutButton("record scores",
                e -> exitToThisPage());
        SpecialUserPage.super.enterToSubPanel(id);
    }

    @Override
    public void exitToThisPage() {
        removeRow(recordsList.getId());
        recordsList = null;
        SpecialUserPage.super.exitToThisPage();
    }
}
