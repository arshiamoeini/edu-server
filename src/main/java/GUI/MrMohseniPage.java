package GUI;

import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

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

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        //TODO
    }
}
