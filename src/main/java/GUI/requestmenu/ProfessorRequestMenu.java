package GUI.requestmenu;

import GUI.*;
import LOGIC.Command;
import MODELS.FacultyTemp;
import MODELS.StudentTemp;
import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfessorRequestMenu implements RequestMenuPanel {
    private JPanel panel;
    private JTabbedPane professor;
    private JPanel recommendationField;
    public ProfessorRequestMenu() {
      //  recommendationInit();
  //      if (Command.getInstance().isTheUserAEducationalAssistant()) {
   //         educationalRequestsInit();
    //    }
    }
    private void recommendationInit() {
        recommendationField.removeAll();
        recommendationField.add(new DemoList() {
            {
                columnsTitle = new String[] {"student name", "write and send","recommendation text"};
                designTopics();
                for (StudentTemp student: Command.getInstance().getRecommendedStudent()) {
                    addRow(student);
                }
            }
            private void addRow(StudentTemp student) {
                gbcFiller.gridy = rowsCounter++;
                columnCounter = 0;
                addCopyableTextInRow(student.getName());

                JButton writeButton = new JButton("write");
                CellPane writeAndSendButton = new CellPane(writeButton);
                gbcFiller.gridx = columnCounter++;
                pane.add(writeAndSendButton, gbcFiller);
                CellPane recommendationText = addCopyableTextInRowForEdit("");

                writeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String text = String.format("I %s certify that Mr. / Mrs. %s with student number %s, \n has completed courses ... \n with a grade of ... \n and has also worked as a teaching assistant in courses ... .",
                                Command.getInstance().getNameOfUser(),
                                student.getName(),
                                student.getId());
                        ((JTextArea) recommendationText.getLabel()).setText(text);
                        recommendationText.setEditable();

                        JButton sendButton = new JButton("send");
                        writeAndSendButton.setButton(sendButton);
                        sendButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Command.getInstance().sendRecommendation(student, recommendationText.getText());
                                recommendationInit();
                            }
                        });
                        recommendationField.revalidate();
                    }
                });
            }
        });
        recommendationField.revalidate();
    }
    private void educationalRequestsInit() {
        if (professor.getTabCount() == 2)  professor.removeTabAt(1);
        professor.addTab("educational request", new DemoList() {
            {
                columnsTitle = new String[] {"request", "message", "student name", "Ok", "reject"};
                designTopics();
                for (FacultyTemp.EducationalRequest request: Command.getInstance().getFaculty().getEducationalRequests()) {
                    addRow(request);
                }
            }

            private void addRow(FacultyTemp.EducationalRequest request) {
                gbcFiller.gridy = rowsCounter++;
                columnCounter = 0;
                addCopyableTextInRow(request.getRequestType().getMassage());
                addCopyableTextInRow(request.getMassage());
                addCopyableTextInRow(request.getStudent().getName());

                JButton okButton = new JButton("Ok");
                JButton rejectButton = new JButton("reject");
                gbcFiller.gridx = 3;
                pane.add(new CellPane(okButton), gbcFiller);
                gbcFiller.gridx = 4;
                pane.add(new CellPane(rejectButton), gbcFiller);

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Command.getInstance().acceptingRequest(request);
                        educationalRequestsInit();
                    }
                });
                rejectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Command.getInstance().rejectingRequest(request);
                        educationalRequestsInit();
                    }
                });
            }
        });
        panel.revalidate();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public JTabbedPane getRootPage() {
        return professor;
    }

    @Override
    public void setExit() {
        ManePagePanelFactory.setOutButtonToExitToMainPage();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
    }
}
