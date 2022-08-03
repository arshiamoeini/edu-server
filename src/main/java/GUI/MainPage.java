package GUI;

import client.Pulsator;
import database.User;
import shared.UserType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class MainPage implements PanelDesigner {
    private JPanel pane;
    private JPanel info;
    private JPanel logo;
    private RealTime realTime;
    private JLabel lastSubmmition;
    private JPanel out;
    ManePagePanelFactory factory = new ManePagePanelFactory();
    private static Runnable goOut;
    public MainPage(Runnable goOut) {
        this.goOut = goOut;
        lastSubmmition.setText(RealTime.dateAndTime(LocalDateTime.now()));

        UserType userType = UserConstantInformation.getInstance().getUserType();
        pane.add(factory.build(userType, out).getPanel());
    }

    @Override
    public JPanel getPanel() {
        return pane;
    }

    public static JButton getOutButton() {
        JButton button = new JButton("out"); //TODO Config
        button.addActionListener(e -> {
            Pulsator.getInstance().interrupt();
            goOut.run();
        });
        return button;
    }
}
