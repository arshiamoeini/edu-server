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

public class GraduateRequestsMenu implements RequestMenuPanel {
    private JTabbedPane graduateStudent;
    private JPanel recommendationField;
    private JPanel panel;
    private JButton accommodationButton;
    private JLabel showAccommodationResualt;
    private JPanel certificateField;
    private JPanel withdrawalField;
    private CertificateMenu certificateMenuDesigner;

    public GraduateRequestsMenu() {
        recommendationInit();
        certificateInit();
        accommodationInit();
        withdrawalField.add(new WithdrawalMenu().getPanel());
    }

    private void accommodationInit() {
        accommodationButton.addActionListener(e -> {
            Client.getInstance().send(RequestType.ADD_ACCOMMODATION_REQUEST,
                    UserConstantInformation.getInstance().getUserId());
            Logger.logInfo("Send accommodation result for user: "+Command.getInstance().getNameOfUser());
        });
    }

    private void certificateInit() {
        certificateMenuDesigner = new CertificateMenu();
        certificateField.add(certificateMenuDesigner.getPanel());
    }
    private void recommendationInit() {
        RecommendationMenu recommendationMenu = new RecommendationMenu();
        graduateStudent.addChangeListener(new SelectRequestMenuHandler(recommendationMenu, panel));
        this.recommendationField.add(recommendationMenu.getPanel());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public JTabbedPane getRootPage() {
        return graduateStudent;
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
