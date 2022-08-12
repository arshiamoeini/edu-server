package GUI;

import client.Client;
import shared.RequestType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class addStudentPanel extends DemoSet {
    private JPanel titles;
    private JPanel nullRow;
    private JComboBox programSelector;
    private JButton addButton = new JButton("add") {
        {
            addActionListener(e -> {
                RowPanel dataRow = getRow(1);
                Object[] inputData = getInputDataInRow(dataRow);
                Client.getSender().send(RequestType.ADD_STUDENT,
                        inputData,
                        UserConstantInformation.getInstance().getFacultyName());
            });
        }

        private Object[] getInputDataInRow(RowPanel dataRow) {
            ArrayList<Object> objects = new ArrayList<>();
            for (int i = 0; i < dataRow.length(); i++) {
                Component component = dataRow.getCellPane(i).getLabel();
                if (component instanceof JComboBox) {
                    objects.add(((JComboBox<?>) component).getSelectedIndex());
                } else if (component instanceof JTextArea){
                    objects.add(((JTextArea) component).getText());
                }
            }
            return objects.toArray();
        }
    };

    public addStudentPanel() {
        designTopics();
        RowPanel rowPanel = designNullRow();
        addRow(1, 1, rowPanel);
    }

    private RowPanel designNullRow() {
        RowPanel rowPanel = new RowPanel();
        rowPanel.addComponents(addButton);
        addNullRows(rowPanel);
        return rowPanel;
    }

    private void addNullRows(RowPanel rowPanel) {
        for (Component component: nullRow.getComponents()) {
            if (component instanceof JLabel) {
                rowPanel.addCopyableDataInRow(((JLabel) component).getText());
                rowPanel.getCellPane(rowPanel.length() - 1).setEditable();
            } else {
                rowPanel.addComponents(component);
            }
        }
    }

    @Override
    public JPanel getTitles() {
        return titles;
    }
}
