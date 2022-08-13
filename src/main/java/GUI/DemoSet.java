package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DemoSet {
    private JPanel panel;
    private JPanel panel1;
    private Map<Long, RowPanel> rowPanelMap;

    public DemoSet() {
        rowPanelMap = new HashMap<>();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
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
        panel1.add(rowPanel, index); //topic first
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

    public void removeRow(Long id) {
        if (id == 0) {
            return;
        }
        panel1.remove(rowPanelMap.remove(id));
    }

    public boolean contain(long id) {
        return rowPanelMap.containsKey(id);
    }

    public JPanel getListPanel() {
        return panel;
    }

    protected void removeAll() {
        panel1.removeAll();
        rowPanelMap.clear();
    }
    public Set<Map.Entry<Long, RowPanel>> getElements() {
        return rowPanelMap.entrySet();
    }
}
