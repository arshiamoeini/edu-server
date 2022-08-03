package GUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DemoList extends JScrollPane {
    protected String[] columnsTitle;

  //  int[] padeSizeInRow = {};

    protected JPanel pane = new JPanel();

    protected int columnCounter = 0;
    protected int rowsCounter = 1;
    protected GridBagConstraints gbcFiller = new GridBagConstraints();

    public DemoList() {
        //setting
        setSize(new Dimension(MainFrame.MAIN_WIDTH - 50, MainFrame.MAIN_HEIGHT - 50));
        setBackground(Color.RED);
        setOpaque(true);

        pane.setLayout(new GridBagLayout());
        gbcFiller.fill = GridBagConstraints.BOTH;
        setViewportView(pane);
    }

    protected void designTopics() {
        //gbcFiller.insets = new Insets(3, 3, 3, 3);
        gbcFiller.gridy = 0;
        for (int i = 0; i < columnsTitle.length; ++i) {
            setComponentAtRow(new CellPane(columnsTitle[i]), columnCounter++); //after pass value ++ apply
        }
    }
    protected void setComponentAtRow(Component label, int index){
        gbcFiller.gridx = index;
        pane.add(label, gbcFiller);
    }
    protected void addCopyableTextInRow(String text) {
        gbcFiller.gridx = (columnCounter++);
        pane.add(new CellPane(text, false), gbcFiller);
    }
    protected CellPane addCopyableTextInRowForEdit(String text) {
        gbcFiller.gridx = (columnCounter++);
        CellPane output = new CellPane(text, false);
        pane.add(output, gbcFiller);
        return output;
    }
    protected static String joinArrayListWithEndLine(ArrayList<Integer> array) {
        return array.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public void addSubjectRow(int id,
                       int credit,
                       String name,
                       ArrayList<Integer> prerequisite,
                       ArrayList<Integer> coRequisite,
                       int capacity,
                       int registrationNumber,
                       String professorName,
                       LocalDateTime examDate) {
        addCopyableTextInRow(String.valueOf(id));
        addCopyableTextInRow(String.valueOf(credit));
        addCopyableTextInRow(name);
        addCopyableTextInRow(
                "prerequisite:\n" +
                        joinArrayListWithEndLine(prerequisite) +
                        "\nco-requisite:\n" +
                        joinArrayListWithEndLine(coRequisite));
        addCopyableTextInRow(String.valueOf(capacity));
        addCopyableTextInRow(String.valueOf(registrationNumber));
        addCopyableTextInRow(professorName);
        addCopyableTextInRow(RealTime.dateAndTime(examDate));
        // pane.getComponent()
    }
}
