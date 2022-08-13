package GUI.takeclasses;

import GUI.DemoSet;
import GUI.RowPanel;
import GUI.SubjectList;
import GUI.UserConstantInformation;
import client.Client;
import shared.RequestType;

import javax.swing.*;

public class TakingClassroomList extends SubjectList {

    private JRadioButton tickRadioButton;
    private JButton takeButton;
    private JButton sendRequestToTakeButton;
    protected JButton removeButton;
    protected JButton changeGroupButton;
    protected DemoSet tickedClassroom;
    protected boolean tickedList = false;
    public TakingClassroomList() {
        tickedClassroom = this;
        tickedList = true;
    }

    public TakingClassroomList(TakingClassroomList tickedClassroomList) {
        tickedClassroom = tickedClassroomList;
    }

    @Override
    public void addRow(long id, Object[] inRow) {
        RowPanel rowPanel = getRow(id);
        rowPanel.addComponents(getTickRadioButton(id, tickedList || tickedClassroom.contain(id))); //if is ticked
        super.addRow(id, inRow);
        rowPanel.addComponents(getTakeButton(id), getSendRequestToTakeButton(id));
    }

    protected JRadioButton getTickRadioButton(long idOfClassroom, boolean selected) {
        return new JRadioButton(tickRadioButton.getText()) {
            {
                setSelected(selected);
                addActionListener(e -> {
                    RowPanel rowPanel = getRow(idOfClassroom);
                    if (isSelected()) {
                        if(!tickedClassroom.contain(idOfClassroom)) tickedClassroom.addRow(idOfClassroom, rowPanel);
                    } else{
                        if(tickedClassroom.contain(idOfClassroom)) tickedClassroom.removeRow(idOfClassroom);
                    }
                });
            }
        };
    }

    private JButton getTakeButton(long idOfClassroom) {
        return new JButton(takeButton.getText()) {
            {
                addActionListener(e -> Client.getSender().send(RequestType.ADD_TAKE_REQUSET,
                        idOfClassroom, UserConstantInformation.getInstance().getUserId()));
            }
        };
    }

    private JButton getSendRequestToTakeButton(long idOfClasseoom) {
        return new JButton(sendRequestToTakeButton.getText()) {
            {
                addActionListener(e -> Client.getSender().send(RequestType.ADD_REQUEST_TO_GET_CLASSROOM,
                        idOfClasseoom, UserConstantInformation.getInstance().getUserId()));
            }
        };
    }


}
