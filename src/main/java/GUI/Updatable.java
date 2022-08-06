package GUI;

import client.Pulsator;
import com.google.gson.Gson;
import shared.RequestType;

import java.util.ArrayList;

public interface Updatable extends PanelDesigner {
    default void enterPage() {
        observePulsator();
        setExit();
    }

    default void observePulsator() {
        Pulsator.getInstance().update(this);
    }
    void setExit();

    RequestType getUpdateRequest();

    void update(ArrayList<String> data, Gson gson) throws Exception;
}
