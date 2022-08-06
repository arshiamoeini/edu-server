package GUI;

import shared.UserType;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UserConstantInformation {
    private static UserConstantInformation instance;

    static {
        instance = new UserConstantInformation();
    }

    UserType userType;
    private JPanel handyButtons;
    private JButton outButton;
    private long userID;

    public static UserConstantInformation getInstance() {
        return instance;
    }
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setOutField(JPanel handyButtons, JButton outButton) {
        this.handyButtons = handyButtons;
        this.outButton = outButton;
    }
    public void setOutButton(String newName, ActionListener action) {
        outButton.setText(newName);
        for (ActionListener al: outButton.getActionListeners()) {
            outButton.removeActionListener(al);
        }
        outButton.addActionListener(action);
        handyButtons.repaint();
    }

    public long getUserId() {
        return userID;
    }
    public void setUserId(long userID) {
        this.userID = userID;
    }
}
