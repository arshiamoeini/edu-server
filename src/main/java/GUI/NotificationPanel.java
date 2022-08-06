package GUI;

import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class NotificationPanel implements MainPagePanel{
    private JPanel panel;
    private JTextArea textArea1;
    private JPanel optionalChoseFiled;

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
}
