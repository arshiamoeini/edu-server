package GUI.recordaffairs;

import GUI.RowPanel;
import LOGIC.Logger;
import client.Client;
import shared.ClassroomRatingData;
import shared.RequestType;

import javax.swing.*;

public class answerRecordList extends  RecordsList{
    public answerRecordList(long id, boolean editable, Runnable exit) {
        super(id, editable, exit);
        operation.removeAll();
        operation.add(getFinalButton());
    }

    private JButton getFinalButton() {
        return new JButton("final") {
            {
                addActionListener(e -> {
                    Client.getSender().send(RequestType.GO_FINAL_FOR_CLASSROOM, id);
                    destroyAll();
                });
            }
        };
    }

    @Override
    protected void updateRate(ClassroomRatingData rating) {
        RowPanel rowPanel = getRow(rating.getId());
        rowPanel.getCellPane(3).setText(rating.getProtest());
    }

    @Override
    protected void addNewRate(ClassroomRatingData rating) {
        super.addNewRate(rating);
        RowPanel rowPanel = getRow(rating.getId());
        rowPanel.getCellPane(4).setEditable();
        rowPanel.addComponents(new JButton("send answer") {
            {
                addActionListener(e -> {
                    try {
                        sendEdits(rating.getId(), rowPanel.getText(2), rowPanel.getText(4));
                    } catch (Exception error) {
                        System.out.println("not valid score");
                        Logger.logInfo((this.toString()) + "try to score with not valid value");
                    }
                });
            }
        });
    }


}
