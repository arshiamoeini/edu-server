package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DemoSet {
    private JPanel panel;
    private JPanel pane;
    private Map<Long, RowPanel> rowPanelMap;

    public DemoSet() {
        rowPanelMap = new HashMap<>();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    }

    protected void designTopics() {
        RowPanel titleRow = new RowPanel();
        for (Component component: getTitles().getComponents()) if (component instanceof JLabel){
            titleRow.addNotCopyableDataInRow(((JLabel) component).getText());
        }
        addRow(0, 0, titleRow);
    }

    public JPanel getTitles() {
        return null;
    }

    public void addRow(int index, long id, RowPanel rowPanel) {
        pane.add(rowPanel, index); //topic first
        rowPanelMap.put(id, rowPanel);
    }
    public void addRow(long id, RowPanel rowPanel) {
        addRow(1, id, rowPanel);
    }
    public RowPanel getRow(long id) {
        return rowPanelMap.get(id);
    }

    public Set<Long> getRowsId() {
        return rowPanelMap.keySet();
    }

    public void remove(Long id) {
        pane.remove(rowPanelMap.remove(id));
    }

    public boolean contain(long id) {
        return rowPanelMap.containsKey(id);
    }

    public JPanel getListPanel() {
        return panel;
    }

    protected void removeAll() {
        pane.removeAll();
        rowPanelMap.clear();
    }
}
