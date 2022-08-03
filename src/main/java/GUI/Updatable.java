package GUI;

import client.Pulsator;
import shared.RequestType;

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
}
