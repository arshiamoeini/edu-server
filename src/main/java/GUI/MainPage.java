package GUI;

import client.Pulsator;
import shared.UserType;

import javax.swing.*;
import java.time.LocalDateTime;

public class MainPage implements PanelDesigner {
    private JPanel pane;
    private JPanel info;
    private JPanel logo;
    private RealTime realTime;
    private JLabel lastSubmmition;
    private JPanel out;
    private JButton outButton;
    ManePagePanelFactory factory = new ManePagePanelFactory();
    private static Runnable goOut;
    public MainPage(Runnable goOut) {
        this.goOut = goOut;
        lastSubmmition.setText(RealTime.dateAndTime(LocalDateTime.now()));

        UserConstantInformation.getInstance().setOutField(out, outButton);
        UserType userType = UserConstantInformation.getInstance().getUserType();
        pane.add(factory.build(userType, out).getPanel());
    }

    @Override
    public JPanel getPanel() {
        return pane;
    }

    public static void setOutButtonToLogOut() {
        UserConstantInformation.getInstance().setOutButton("out", e -> {
                Pulsator.getInstance().interrupt();
                goOut.run();
            } ); //TODO Config
    }
}
