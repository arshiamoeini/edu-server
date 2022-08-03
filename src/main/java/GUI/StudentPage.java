package GUI;

import shared.RequestType;

import javax.swing.*;

public class StudentPage implements NormalUserPage {
    private JPanel panel;
    private JTabbedPane main;
    private JTabbedPane registrationMatters;
    private JPanel professorList;
    private JComboBox comboBox1;
    private final JPanel out;

    public StudentPage(JPanel out) {
        main.addChangeListener(new SelectMenuHandler(this, panel));
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
        out.removeAll();
        out.add(MainPage.getOutButton());
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }
}
