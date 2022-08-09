package GUI.REQUEST_MENU;

import GUI.RealTime;
import GUI.UserConstantInformation;
import LOGIC.Command;
import LOGIC.Logger;
import client.Client;
import shared.RequestType;

import javax.swing.*;
import java.time.LocalDateTime;

public class CertificateMenu {
    private JPanel certificateMenu;
    private JButton certificateButton;
    private JPanel panel;

    public CertificateMenu() {
        certificateButton.addActionListener(e -> {
            Client.getInstance().send(RequestType.ADD_CERTIFICATE,
                    UserConstantInformation.getInstance().getUserId());
            Logger.logInfo("Send certification for user:"+Command.getInstance().getNameOfUser());
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
