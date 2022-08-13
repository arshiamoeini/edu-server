package GUI.requestmenu;

import GUI.SelectMenuHandler;
import GUI.Updatable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class SelectRequestMenuHandler extends SelectMenuHandler {

    public SelectRequestMenuHandler(Updatable mainPage, JPanel panel) {
        super(mainPage, panel);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        int index = ((JTabbedPane) changeEvent.getSource()).getSelectedIndex();

        if (index < pages.size() && pages.get(index) != null) {
            pages.get(index).enterPage();
        }

        panel.revalidate();
        panel.repaint();
    }
}
