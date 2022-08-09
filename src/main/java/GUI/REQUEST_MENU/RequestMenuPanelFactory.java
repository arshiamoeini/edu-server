package GUI.REQUEST_MENU;

import GUI.RequestMenuPanel;
import LOGIC.Command;
import shared.UserType;

public class RequestMenuPanelFactory {
    private RequestMenuPanel panel;

    public RequestMenuPanel build(UserType userType) {
        if (userType.isProfessor()) {
            panel = new ProfessorRequestMenu();
        } else {
            switch (userType) {
                case BachelorStudent:
                    panel = new BachelorStudentRequestsMenu();
                    break;
                case GraduateStudent:
                    panel = new GraduateRequestsMenu();
                    break;
                case PHDStudent:
                    panel = new PHDRequestsMenu();
            }
        }
        return panel;
    }
}
