package GUI;

import shared.Identifier;
import shared.LoginResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class Login implements PanelDesigner {
    private static Login instance;

    private JPanel loginPane;
    private JTextField userIDField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JButton loginButton;
    private JLabel userIDLabel;
    private JLabel PasswordLabel;
    private JLabel capcha;
    private JButton retryButton;
    private JTextField capchaGuess;

    private CapchaGenerator capchaGenerator;
    // private Function<Identifier, LoginResult> login;
    public Login(Function<Identifier, LoginResult> login) {
        //setting
        setListener(login);
        loginPane.setBounds(0, 0, 30, 50);
        capchaGenerator = new CapchaGenerator();
        newCapcha();
      //  this.login = login;
    }

    public static Login getInstance() { return instance; }

    public static void setInstance(Function<Identifier, LoginResult> login) {
        instance = new Login(login);
    }

    private void setListener(Function<Identifier, LoginResult> login) {
        showPassword.addActionListener(e -> passwordField.setEchoChar(showPassword.isSelected() ? (char)0 : '*'));
        loginButton.addActionListener(e -> {
            Identifier date = new Identifier(userIDField.getText(), new String(passwordField.getPassword()));

            if (!capchaGuess.getText().equals(String.valueOf(capchaGenerator.gerCapchaCode()))) {
                showMessage("capcha Invalid"); //TODO config
            } else if (date.isValid()) {
                handelResult(login.apply(date));
            } else {
                showMessage("Invalid input"); //TODO config
            }
        });
        retryButton.addActionListener(e -> { newCapcha(); });
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
    private void newCapcha() {
        capchaGenerator.generateCapcha();
        capcha.setIcon(capchaGenerator.getCapchaImage());
        loginPane.repaint();
    }

    @Override
    public JPanel getPanel() { return loginPane; }

    private void createUIComponents() {

    }
}
