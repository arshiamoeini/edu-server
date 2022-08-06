package GUI;

import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class ProfessorPage implements NormalUserPage {
    private JPanel panel;
    private JTabbedPane main;
    private JPanel registrationMatters;

    private final JPanel out;
    public ProfessorPage(JPanel out) {
        main.addChangeListener(new SelectMenuHandler(this, panel));
        this.out = out;
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
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        //Todo
    }
}
