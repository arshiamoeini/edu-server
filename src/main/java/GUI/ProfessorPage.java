package GUI;

import GUI.recordaffairs.RecordScores;
import client.Client;
import com.google.gson.Gson;
import shared.MasterLevel;
import shared.RequestType;
import shared.UserType;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProfessorPage implements NormalUserPage {
    private JPanel panel;
    private JTabbedPane main;
    private JPanel registrationMatters;
    private JLabel name;
    private JLabel email;
    private JPanel addUser;
    private JLabel profileName;
    private JLabel profileEmail;
    private JLabel nationalCode;
    private JLabel userId;
    private JLabel phoneNumber;
    private JLabel faculty;
    private JLabel roomNumber;
    private JLabel masterLevel;
    private JRadioButton defineTakingClassesForRadioButton;
    private JComboBox selectedEnteryYear;
    private JPanel programSelectField;
    private JTextArea startTakingTime;
    private JTextArea lengthOfTakingTime;
    private JPanel selectTakeClassesTimePage;
    private JPanel filterAndSelectTimeForTakingClassesPanel;
    private JButton okForSetTakingClassesTime;

    private final JPanel out;
    private DemoSet studentAdder;
    public ProfessorPage(JPanel out) {
        main.addChangeListener(new SelectMenuHandler(this, panel));
        addCartPanel(6, new RecordScores());
        this.out = out;
        studentAdderInit();
        takeClassesPageInit();
        userId.setText(String.valueOf(UserConstantInformation.getInstance().getUserId()));
        faculty.setText(UserConstantInformation.getInstance().getFacultyName());
    }


    private void takeClassesPageInit() {
        if (UserConstantInformation.getInstance().getUserType() != UserType.EducationalAssistant) {
            selectTakeClassesTimePage.setVisible(false);
        } else {
            defineTakingClassesForRadioButton.addActionListener(e ->
                    defineOrUndefineTakingClasses(defineTakingClassesForRadioButton.isSelected()));
            getDataForSelectingTimePanelInit();
        }
    }
    private void getDataForSelectingTimePanelInit() {
        startTakingTime.setText(RealTime.dateAndTime(LocalDateTime.now()));
        OptionCentricText programSelector = new OptionCentricText(OptionCentricText.OptionsFrom.Program);
        programSelectField.add(programSelector);
        okForSetTakingClassesTime.addActionListener(e -> sendSelectedTime(
                (String) selectedEnteryYear.getSelectedItem(), programSelector.getSelectedIndex(),
                startTakingTime.getText(), lengthOfTakingTime.getText()));
    }

    private void sendSelectedTime(String entryYear, int programIndex, String startTakingTimeText, String lengthOfTakingTimeText) {
        try {
            LocalDateTime startingTime = RealTime.readLocalDateTime(startTakingTimeText);
            int length = Integer.parseInt(lengthOfTakingTimeText);
            Client.getSender().send(RequestType.SET_TIME_FOR_TAKING_CLASSES,
                    length, startingTime, programIndex, entryYear, UserConstantInformation.getInstance().getUserId());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(panel, "Invalid input");
        }

    }

    private void defineOrUndefineTakingClasses(boolean selected) {
        Client.getSender().send(RequestType.SET_IS_TIME_FOR_TAKING_CLASSES,
                    selected, UserConstantInformation.getInstance().getFacultyName());
        filterAndSelectTimeForTakingClassesPanel.setVisible(selected);
        selectTakeClassesTimePage.repaint();
        selectTakeClassesTimePage.revalidate();
    }

    private void studentAdderInit() {
        if (UserConstantInformation.getInstance().getUserType() == UserType.EducationalAssistant) {
            studentAdder = new addStudentPanel();
            addUser.add(studentAdder.getListPanel());
        }
    }

    @Override
    public JPanel getPanel() { return panel; }
    @Override
    public JTabbedPane getRootPage() {
        return main;
    }

    @Override
    public void setExit() {
        MainPage.setOutButtonToLogOut();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.GET_PROFESSOR_MAIN_PAGE_DATA;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        updateUserField(data);
        roomNumber.setText(data.get(4));
        masterLevel.setText(MasterLevel.valueOf(data.get(5)).getMassage());
    }
    private void updateUserField(ArrayList<String> data) {
        name.setText(data.get(0));
        email.setText(data.get(1));
        profileName.setText(data.get(0));
        profileEmail.setText(data.get(1));
        nationalCode.setText(data.get(2));
        phoneNumber.setText(data.get(3));
    }
}
