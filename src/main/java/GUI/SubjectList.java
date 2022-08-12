package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SubjectList extends DemoSet {
    private JPanel titles;

    @Override
    public JPanel getTitles() {
        return titles;
    }

    public SubjectList() {
        super.designTopics();
    }



    public void addRow(long id, Object[] inRow) {
        if (!contain(id)) addRow(id, new RowPanel());
        RowPanel rowPanel = getRow(id);
        for (int i = 0; i < 3; i++) {
            rowPanel.addCopyableDataInRow(inRow[i]);
        }
        rowPanel.addCopyableDataInRow("prerequisite:\n" +
                joinArrayListWithEndLine((ArrayList<Integer>) inRow[3]) +
                "\nco-requisite:\n" +
                joinArrayListWithEndLine((ArrayList<Integer>) inRow[4]));
        for (int i = 5; i < inRow.length; i++) {
            rowPanel.addCopyableDataInRow(inRow[i]);
        }
    }
    protected static String joinArrayListWithEndLine(ArrayList<Integer> array) {
        return array.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
