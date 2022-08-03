package GUI.REQUEST_MENU;

import GUI.RequestMenuPanel;
import LOGIC.Command;
import shared.UserType;

public class RequestMenuPanelFactory {
    private RequestMenuPanel panel;

    public RequestMenuPanel build(UserType userType) {
        return null;
                //Command.getInstance().isTheUSerABachelorStudent() ?
                //        new BachelorStudentRequestsMenu().getPanel() :
                 //       Command.getInstance().isTheUserAGraduateStudent() ?
                 //               new GraduateRequestsMenu().getPanel() :
                  //              Command.getInstance().isTheUserAPHDStudent() ?
                  //                      new PHDRequestsMenu().getPanel() :
                   //                     Command.getInstance().isTheUserAProfessor() ?
                   //                             new ProfessorRequestMenu().getPanel() :
                   //                             null);
    }
}
