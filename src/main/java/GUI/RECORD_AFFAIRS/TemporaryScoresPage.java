package GUI.RECORD_AFFAIRS;

import GUI.CellPane;
import GUI.DemoList;
import GUI.PanelDesigner;
import LOGIC.Command;
import MODELS.ClassroomTemp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemporaryScoresPage implements PanelDesigner {
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JPanel records;

    public TemporaryScoresPage() {
        records.add(new DemoList() {
            {
                //TODO not clean
                columnsTitle = new String[] {"course name", "teacher name", "recorded scores", "send", "register a protest", "see answer"};
                designTopics();
                for (ClassroomTemp.classRating rating: Command.getInstance().getRegisteredRecords()) {
                    addRow(rating);
                }
            }

            private void addRow(ClassroomTemp.classRating rating) {
                gbcFiller.gridy = rowsCounter++;
                columnCounter = 0;
                addCopyableTextInRow(rating.getClassroom().getCourse().getName());
                addCopyableTextInRow(rating.getClassroom().getProfessorName());
                addCopyableTextInRow(String.valueOf(rating.getScore()));
                JButton sendButton = new JButton("send");
                gbcFiller.gridx = columnCounter++;
                pane.add(sendButton, gbcFiller);
                CellPane text = addCopyableTextInRowForEdit(rating.getProtest());
                text.setEditable();
                addCopyableTextInRow(rating.getAnswer());

                sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rating.setProtest(text.getText());
                    }
                });
            }
        });

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
