package GUI;

import javax.swing.*;

public class StudentMenus extends JMenuBar {
    public static StudentMenus instance;

    JMenu registerMenu = new JMenu("register");
    JMenu educationalServicesMenu = new JMenu("educational services");
    JMenu ReportMenu = new JMenu("report");

    static {
        instance = new StudentMenus();
    }
    // public static String MENUS_NAME[] = {"register", "educational services", "nomarat"};
    public StudentMenus() {
        addMenus();

        registerMenu.add(new JMenuItem("salam"));//MENUS_NAME[0]));
        setBounds(0, 0, 120, 120);
    }

    public static StudentMenus getInstance() { return instance; }

    public void addMenus() {
        add(registerMenu);
        add(educationalServicesMenu);
        add(ReportMenu);
    }
}
