package GUI.requestmenu;

import GUI.UserConstantInformation;
import client.Client;
import shared.RequestType;

import javax.swing.*;

public class WithdrawalMenu {
    private JButton sendWithdrawalRequestButton;
    private JPanel panel;

    public WithdrawalMenu() {
        sendWithdrawalRequestButton.addActionListener(e -> {
            Client.getInstance().send(RequestType.ADD_WITHDRAWAL_REQUEST,
                    UserConstantInformation.getInstance().getUserId());
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
