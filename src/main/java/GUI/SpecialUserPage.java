package GUI;

import java.awt.*;

public interface SpecialUserPage extends MainPagePanel {
    //getCard give card layout with two card in getPanel of Page
    //second card is used for show sub pages
    //so first in enterToSubPanel we fill second card to show requested sub page and then setOutButton and then call
    // default method
    default void enterToSubPanel(int id) {
        getCards().show(getPanel(), CART + "2");
        getPanel().repaint();
    }
    default CardLayout getCards() {
        return (CardLayout) getPanel().getLayout();
    }
    default void exitToThisPage() {
        getCards().show(getPanel(), CART + "1");
        this.enterPage();
        getPanel().repaint();
    }
}
