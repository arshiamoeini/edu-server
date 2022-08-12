package GUI.REQUEST_MENU;

import GUI.SelectMenuHandler;
import GUI.Updatable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class SelectRequestMenuHandler extends SelectMenuHandler {

    public SelectRequestMenuHandler(Updatable mainPage, JPanel panel) {
        super(mainPage, panel);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        int index = ((JTabbedPane) changeEvent.getSource()).getSelectedIndex();

        if (index < pages.size()) {
            pages.get(index).enterPage();
        }

        panel.revalidate();
        panel.repaint();
    }
}
