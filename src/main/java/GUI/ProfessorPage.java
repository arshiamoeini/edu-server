package GUI;

import com.google.gson.Gson;
import shared.MasterLevel;
import shared.RequestType;
import shared.UserType;

import javax.swing.*;
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

    private final JPanel out;
    private DemoSet studentAdder;
    public ProfessorPage(JPanel out) {
        main.addChangeListener(new SelectMenuHandler(this, panel));
        this.out = out;
        studentAdderInit();
        userId.setText(String.valueOf(UserConstantInformation.getInstance().getUserId()));
        faculty.setText(UserConstantInformation.getInstance().getFacultyName());
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
