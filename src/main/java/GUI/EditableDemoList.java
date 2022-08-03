package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class EditableDemoList extends DemoList {
    public EditableDemoList() {
        super();
    }

    @Override
    protected void designTopics() {
        //to more option for add and delete
        gbcFiller.gridy = 0;
        setComponentAtRow(new CellPane(new adderButton()), columnCounter++);
        super.designTopics();
        setComponentAtRow(new CellPane("delete"), columnCounter++);
    }
    protected CellPane getCellPane(int row, int column) {
        return (CellPane) pane.getComponent(row * (columnsTitle.length + 2) + column);
    }

    protected abstract void addActionHandler();
    public class adderButton extends JButton {
        public adderButton() {
            setText("add");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addActionHandler();
                }
            });
        }
    }

    protected abstract void editActionHandler(int rowNumber);
    public class editButton extends JButton {
        private int row;

        public editButton(int row) {
            this.row = row;

            setText("edit");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editActionHandler(row);
                }
            });
        }
    }

    protected abstract void deleteActionHandler(int rowNumber);
    public class deleteButton extends JButton {
        private int row;

        public deleteButton(int row) {
            this.row = row;

            setText("delete");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteActionHandler(row);
                }
            });
        }
    }
}
