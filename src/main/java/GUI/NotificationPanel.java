package GUI;

import client.Client;
import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class NotificationPanel implements MainPagePanel{
    private JPanel panel;
    private JTextArea textArea1;
    private JPanel optionalChoseFiled;
    private Integer id;
    private JButton enterButton;

    public NotificationPanel(JButton enterButton, boolean editable, int id, String text, long sourceUserId) {
        this.enterButton = enterButton;
        textArea1.setText(text);
        textArea1.setEditable(editable);
        addOptionButton("accept", true, id, sourceUserId);//TODO Config
        addOptionButton("reject", false, id, sourceUserId);
    }

    public NotificationPanel(JButton enterButton, String text) {
        this.enterButton = enterButton;
        textArea1.setText(text);
    }

    private void addOptionButton(String name, boolean message, int id, long sourceUserId) {
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            Client.getInstance().send(null, id, sourceUserId, message); //TODO
        });
        optionalChoseFiled.add(button);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setExit() {
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
    }

    public JButton getEnterButton() {
        return enterButton;
    }
}
