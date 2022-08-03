package GUI;

import shared.Identifier;
import shared.LoginResult;

import javax.swing.*;
import java.util.function.Function;

public class LoginTemp implements PanelDesigner {
    private JPanel loginPane;
    private JTextField userIDField;
    private JLabel userIDLabel;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JButton loginButton;
    private JLabel PasswordLabel;

    public LoginTemp() {
    }

    public LoginTemp(Function<Identifier, LoginResult> login) {
        this();
        //setting
        //setListener(login);
        //    loginPane.setBounds(0, 0, 30, 50);
        showPassword.addActionListener(e -> passwordField.setEchoChar(showPassword.isSelected() ? (char)0 : '*'));
        loginButton.addActionListener(e -> {
            Identifier date = new Identifier(userIDField.getText(), new String(passwordField.getPassword()));

            if (date.isValid()) {
                handelResult(login.apply(date));
            } else {
                showMessage("Invalid input"); //TODO config
            }
        });
    }
    public void inti() {
        System.out.println("asdfsadf");
    }
    private void handelResult(LoginResult result) {
        if (result == LoginResult.PASS) {
            showMessage("welcome " + result.getMassage());
        } else {
            showMessage(result.getMassage());
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(loginButton, message);
    }

    @Override
    public JPanel getPanel() {
        return loginPane;
    }
}
