package GUI.requestmenu;

import GUI.ManePagePanelFactory;
import GUI.RequestMenuPanel;
import GUI.UserConstantInformation;
import LOGIC.Command;
import LOGIC.Logger;
import client.Client;
import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class PHDRequestsMenu implements RequestMenuPanel {
    private JTabbedPane PHDStudent;
    private JPanel certificateField;
    private JPanel withdrawalField;
    private JButton requestButton;
    private JLabel showRequestResult;
    private JPanel panel;

    public PHDRequestsMenu() {
        BachelorStudentRequestsMenu bachelorStudentRequestsMenu = new BachelorStudentRequestsMenu();

        certificateField.add(new CertificateMenu().getPanel());
        withdrawalField.add(new WithdrawalMenu().getPanel());
        requestButton.addActionListener(e -> {
            Client.getSender().send(RequestType.ADD_REQUEST_TURN_TO_DEFEND_THE_DISSERTATION,
                    UserConstantInformation.getInstance().getUserId());
            Logger.logInfo("Send turn to defend the dissertation for user: "+ Command.getInstance().getNameOfUser());
        });
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public JTabbedPane getRootPage() {
        return PHDStudent;
    }

    @Override
    public void setExit() {
        ManePagePanelFactory.setOutButtonToExitToMainPage();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {

    }
}
