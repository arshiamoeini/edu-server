package GUI;

import shared.UserType;

import javax.swing.*;
import java.awt.*;

public class ManePagePanelFactory {
    private static MainPagePanel panel;
    public MainPagePanel build(UserType userType, JPanel out) {
        if (userType == UserType.MrMohseni) {
            panel = new MrMohseniPage(out);
        } else {
            NormalUserPage page = (userType.isProfessor() ? new ProfessorPage(out) : new StudentPage(out));
            EducationalServicesDesigner servicesDesigner = new EducationalServicesDesigner(userType, out);
            ListDesigner listDesigner = new ListDesigner();
            CoursewarePanel courseware = new CoursewarePanel(out);
            page.addCartPanel(2, listDesigner);
            page.addCartPanel(3, courseware);
            page.addCartPanel(4, servicesDesigner);
            panel = page;
        }
        panel.enterPage();
        return panel;
    }
    public static JButton getGoToMainPageButton() {
        JButton button = new JButton("main page"); //TODO Config
        button.addActionListener(e -> {
            CardLayout cards = (CardLayout) panel.getPanel().getLayout();
            cards.show(panel.getPanel(), NormalUserPage.CART+"1");
            ((NormalUserPage) panel).getRootPage().setSelectedIndex(0);
            panel.enterPage();
            panel.getPanel().repaint();
        });
        return button;
    }
    public static void setOutButtonToExitToMainPage() {
        UserConstantInformation.getInstance().setOutButton("main page", e -> { CardLayout cards = (CardLayout) panel.getPanel().getLayout();
            cards.show(panel.getPanel(), NormalUserPage.CART+"1");
            ((NormalUserPage) panel).getRootPage().setSelectedIndex(0);
            panel.enterPage();
            panel.getPanel().repaint();
        });
    }
}
