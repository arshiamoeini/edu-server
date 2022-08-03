package GUI;

import javax.swing.*;

public interface NormalUserPage extends MainPagePanel {
    String CART = "Card";
    JTabbedPane getRootPage();
    default SelectMenuHandler getSelectMenuHandler() {
        return (SelectMenuHandler) getRootPage().getChangeListeners()[0];
    }
    default void addCartPanel(int cartIndex, Updatable updatable) {
        getPanel().add(CART + cartIndex, updatable.getPanel());
        getSelectMenuHandler().addUpdatable(updatable);
    }
}
