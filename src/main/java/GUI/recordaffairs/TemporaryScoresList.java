package GUI.recordaffairs;

import GUI.DemoSet;
import GUI.RowPanel;
import client.Client;
import shared.RequestType;

import javax.swing.*;
import java.util.function.Function;

public class TemporaryScoresList extends DemoSet {
    private JPanel titles;
    private static int protestRowIndex = 4;
    private Function<Long, JButton> getSendButton = id -> new JButton("send") {
        {
            addActionListener(e -> {
                Client.getSender().send(RequestType.ADD_PROTEST,
                        getRow(id).getText(protestRowIndex),
                        id);
                removeRow(id);
            });
        }
    };

    public TemporaryScoresList() {
        designTopics();
    }

    @Override
    public JPanel getTitles() {
        return titles;
    }

    public void addNew(String courseName, String teacherName, double recordedScore, String registerAProtest, String seeAnswer, Long id) {
        RowPanel rowPanel = new RowPanel();
        rowPanel.addCopyableDataInRow(courseName, teacherName, recordedScore);
        rowPanel.addComponents(getSendButton.apply(id));
        rowPanel.addCopyableDataInRow(registerAProtest, seeAnswer);
        rowPanel.getCellPane(protestRowIndex).setEditable();
        addRow(id, rowPanel);
    }

    public void updateRow(Long id, double recordedScore, String seeAnswer) {
        RowPanel rowPanel = getRow(id);
        rowPanel.getCellPane(2).setText(String.valueOf(recordedScore));
    //    rowPanel.getCellPane(protestRowIndex).setText(registerAProtest);
        rowPanel.getCellPane(5).setText(seeAnswer);
    }
}
