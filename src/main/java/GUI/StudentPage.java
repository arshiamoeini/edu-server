package GUI;

import GUI.recordaffairs.TemporaryScoresPage;
import MODELS.EducationalStatus;
import com.google.gson.Gson;
import shared.Program;
import shared.RequestType;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StudentPage implements NormalUserPage {
    private JPanel panel;
    private JTabbedPane main;
    private JTabbedPane registrationMatters;
    private JPanel professorList;
    private JComboBox comboBox1;
    private JLabel name;
    private JLabel email;
    private JLabel registrationLicense;
    private JLabel registrationTime;
    private JLabel profileName;
    private JLabel profileEmail;
    private JLabel nationalCode;
    private JLabel userId;
    private JLabel phoneNumber;
    private JLabel faculty;
    private JLabel supervisor;
    private JLabel entryYear;
    private JLabel program;
    private JLabel status;
    private final JPanel out;

    public StudentPage(JPanel out) {
        main.addChangeListener(new SelectMenuHandler(this, panel));
        addCartPanel(6, new TemporaryScoresPage());
        this.out = out;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private void createUIComponents() {
     //   table1.setColumnModel();
      //  table1.set
    }

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
        return RequestType.GET_STUDENT_MAIN_PAGE_DATA;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        updateUserField(data);
        registrationLicense.setText(gson.fromJson(data.get(4), Boolean.class) ? "allowed" : "not allowed");
        LocalDateTime time = gson.fromJson(data.get(5), LocalDateTime.class);
        registrationTime.setText(time == null ? "not determined" : RealTime.dateAndTime(time));
        setSpecialField(data, gson);
    }

    private void setSpecialField(ArrayList<String> data, Gson gson) {
        supervisor.setText(data.get(6));
        entryYear.setText(data.get(7));
        program.setText(Program.valueOf(data.get(8)).getMassage());
        status.setText(EducationalStatus.valueOf(data.get(9)).getMassage());
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
