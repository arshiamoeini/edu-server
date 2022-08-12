package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RowPanel extends JPanel {
    protected GridBagConstraints gbcFiller = new GridBagConstraints();
    protected int columnCounter = 0;
    public RowPanel() {
        setLayout(new GridBagLayout());
        gbcFiller.fill = GridBagConstraints.BOTH;
        gbcFiller.gridy = 0;
        gbcFiller.gridx = 0;
    }
    public void addOneComponent(Component component) {
        add(component, gbcFiller);
        gbcFiller.gridx++;
    }
    public void addComponents(Component... components) {
        for (Component component: components) {
            addOneComponent(new CellPane(component));
        }
    }
    public void addCopyableDataInRow(Object... args) {
        for (Object arg : args) {
            addOneComponent(new CellPane(String.valueOf(arg), false));
        }
    }

    public void addNotCopyableDataInRow(Object... args) {
        for (Object arg : args) {
            addOneComponent(new CellPane(String.valueOf(arg)));
        }
    }

    public void addFirst(Component component) {
        List<Component> components = new ArrayList<>(Arrays.asList(getComponents()));
        removeAll();
        components.add(0, new CellPane(component));
        addComponents(components.stream().toArray(Component[]::new));
    }

    public CellPane getCellPane(int n) {
        return (CellPane) getComponent(n);
    }

    public int length() {
        return gbcFiller.gridx;
    }

    public String getText(int n) {
        return getCellPane(n).getText();
    }
}
