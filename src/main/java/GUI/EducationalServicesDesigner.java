package GUI;

import GUI.REQUEST_MENU.*;
import LOGIC.Command;
import shared.RequestType;
import shared.UserType;

import javax.swing.*;

public class EducationalServicesDesigner implements NormalUserPage {
//    private static EducationalServicesDesigner instance;

    private JPanel educationalServices;
    private JPanel weeklySchedule;
    private JPanel examSchedule;
    private JTabbedPane main;
    private final JPanel out;

    public static class WeeklyClassSchedule {
        String name;
        String teacher;
        String time;

        public WeeklyClassSchedule(String name, String teacher, String time) {
            this.name = name;
            this.teacher = teacher;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("%s (%s) %s", name, teacher, time);
        }
    }
  //  static {
   //     instance = new EducationalServicesDesigner();
   // }
    private RequestMenuPanelFactory factory;
    public EducationalServicesDesigner(UserType userType, JPanel out) {
        this.out = out;
        main.addChangeListener(new SelectMenuHandler(this, educationalServices));
        factory = new RequestMenuPanelFactory();
        //addCartPanel(2, factory.build(userType)); //TODO


        //TODO get and SORT weekly class
/*        for (WeeklyClassSchedule clazz:  Command.getInstance().getSchedule()) {
            weeklySchedule.add(new JLabel(clazz.toString()));

        }
        for (WeeklyClassSchedule clazz: Command.getInstance().getExamSchedule()) {
            examSchedule.add(new JLabel(clazz.toString()));
        }*/
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
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }
}
