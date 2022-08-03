package GUI.REQUEST_MENU;

import GUI.PanelDesigner;
import LOGIC.Command;
import LOGIC.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GraduateRequestsMenu implements PanelDesigner {
    private JTabbedPane graduateStudent;
    private JPanel recommendationField;
    private JPanel panel;
    private JButton accommodationButton;
    private JLabel showAccommodationResualt;
    private JPanel certificateField;
    private JPanel withdrawalField;

    public GraduateRequestsMenu() {
        BachelorStudentRequestsMenu bachelorStudentRequestsMenu = new BachelorStudentRequestsMenu();

        recommendationField.add(bachelorStudentRequestsMenu.getRecommendationMenu(), BorderLayout.CENTER);
        certificateField.add(bachelorStudentRequestsMenu.getCertificateMenu());
        accommodationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAccommodationResualt.setText((new Random()).nextBoolean() ? "accept" : "reject");
                showAccommodationResualt.revalidate();
                Logger.logInfo("Send accommodation result for user: "+Command.getInstance().getNameOfUser());
            }
        });
        withdrawalField.add(bachelorStudentRequestsMenu.getWithdrawalMenu());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
