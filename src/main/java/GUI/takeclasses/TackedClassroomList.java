package GUI.takeclasses;

import GUI.MainPagePanel;
import GUI.RowPanel;
import GUI.UserConstantInformation;
import client.Client;
import com.google.gson.Gson;
import shared.ClassroomData;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class TackedClassroomList extends TakingClassroomList implements MainPagePanel {

    public TackedClassroomList(TakingClassroomList tickedClassroomList) {
        super(tickedClassroomList);
    }

    @Override
    public void addRow(long id, Object[] inRow) {
        RowPanel rowPanel = getRow(id);
        rowPanel.addComponents(getTickRadioButton(id, tickedList || tickedClassroom.contain(id))); //if is ticked
        super.addRow(id, inRow);
        rowPanel.addComponents(getRemoveButton(id), getChangeGroupButton(id));
    }

    private JButton getChangeGroupButton(long idOfClassroom) {
        return new JButton(changeGroupButton.getText()) {
            {
                addActionListener(e -> {

                });
            }
        };
    }

    private JButton getRemoveButton(long idOfClassroom) {
        return new JButton(removeButton.getText()) {
            {
                addActionListener(e -> Client.getSender().send(RequestType.REMOVE_TAKED_CLASSROOM,
                        idOfClassroom, UserConstantInformation.getInstance().getUserId()));
            }
        };
    }

    @Override
    public JPanel getPanel() {
        return getListPanel();
    }

    @Override
    public void setExit() {

    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.GET_TAKED_CLASSROOM;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
        if (!data.isEmpty()) {
            Set<ClassroomData> classroomData = data.stream().
                    map(x -> gson.fromJson(x, ClassroomData.class)).collect(Collectors.toSet());
            updateClassrooms(classroomData);
            getListPanel().repaint();
            getListPanel().revalidate();
        }
    }
    private void updateClassrooms(Set<ClassroomData> classroomData) {
        for (ClassroomData classroom: classroomData) {
            if (!contain(classroom.getId())) {
                addNewClassroom(classroom);
            } else {
                updateClassroom(classroom);
            }
        }
    }
    private void updateClassroom(ClassroomData classroom) {
        super.updateRow(classroom.getId(), classroom.getInRow());
    }

    private void addNewClassroom(ClassroomData classroom) {
        addRow(classroom.getId(), classroom.getInRow());
    }

}
