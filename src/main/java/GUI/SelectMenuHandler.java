package GUI;

import LOGIC.Command;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class SelectMenuHandler implements ChangeListener {
    ArrayList<Updatable> pages = new ArrayList<>();
    /*
        the panel is in mainPage as PanelDesigner and Updatable
        panel should have layout = CardLayout and main with type JTabbedPane
        this listener add on the main JTabbedPane that is in component of panel
        so with click on that menus bar we Scroll between cards of panel
     */
    private JPanel panel;
    public SelectMenuHandler(Updatable mainPage, JPanel panel) {
        //TODO fix this system bug
        addUpdatable(mainPage);
        this.panel = panel;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        int index = ((JTabbedPane) changeEvent.getSource()).getSelectedIndex();

        CardLayout cards = (CardLayout) panel.getLayout();
        if (index < pages.size()) {
            cards.show(panel, "Card" + (index + 1));
            pages.get(index).enterPage();
        }

        panel.revalidate();
        panel.repaint();
            /*    cards.first(panel1);
                for (int i = 0;i < index;++i) {
                    cards.next(panel1);
                }*/
        /*
        out.removeAll();
        if (index == 0) {
            out.add(new outMainPageButton());
        } else {
            JButton button = new JButton("main page");
            button.addActionListener(e -> {
                cards.show(panel, "Card1");
                ((JTabbedPane) changeEvent.getSource()).setSelectedIndex(0);
                panel.revalidate();
            });
            out.add(button);
        }*/
    }

    public void addUpdatable(Updatable updatable) {
        pages.add(updatable);
    }

    public class outMainPageButton extends JButton {
        public outMainPageButton() {
            setText("out");
            addActionListener(e -> Command.getInstance().showLoginUser());
        }
    }
}
