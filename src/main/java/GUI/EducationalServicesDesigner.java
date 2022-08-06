package GUI;

import GUI.REQUEST_MENU.*;
import com.google.gson.Gson;
import shared.RequestType;
import shared.UserType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EducationalServicesDesigner implements NormalUserPage {
//    private static EducationalServicesDesigner instance;

    private JPanel educationalServices;
    private JPanel weeklySchedule;
    private JPanel examSchedule;
    private JTabbedPane main;
    private final JPanel out;

    //  static {
   //     instance = new EducationalServicesDesigner();
   // }
    private RequestMenuPanelFactory factory;
    public EducationalServicesDesigner(UserType userType, JPanel out) {
        this.out = out;

        main.addChangeListener(new SelectMenuHandler(this, educationalServices));
        factory = new RequestMenuPanelFactory();
        //addCartPanel(2, factory.build(userType)); //TODO
    }

    @Override
    public JPanel getPanel() {
        return educationalServices;
    }

    @Override
    public JTabbedPane getRootPage() {
        return main;
    }
    @Override
    public void setExit() {
        out.removeAll();
        out.add(ManePagePanelFactory.getGoToMainPageButton());
        out.repaint();
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        String[] weeklyClassTime = gson.fromJson(data.get(0), String[].class);
        String[] weeklyClassExamDate = gson.fromJson(data.get(1), String[].class);

        Arrays.stream(weeklyClassTime).forEach(x -> weeklySchedule.add(new JLabel(x)));
        Arrays.stream(weeklyClassExamDate).forEach(x -> examSchedule.add(new JLabel(x)));
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.GET_WEEKLY_CLASS_DATA;
    }
}
