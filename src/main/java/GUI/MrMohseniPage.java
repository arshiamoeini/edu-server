package GUI;

import shared.RequestType;

import javax.swing.*;

public class MrMohseniPage implements MainPagePanel {
    public MrMohseniPage(JPanel out) {

    }

    @Override
    public void setExit() {

    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }
    @Override
    public JPanel getPanel() {
        return null;
    }
}
