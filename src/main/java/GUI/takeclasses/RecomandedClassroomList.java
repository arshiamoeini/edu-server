package GUI.takeclasses;

import GUI.MainPagePanel;
import com.google.gson.Gson;
import shared.ClassroomData;
import shared.ClassroomTemporaryRate;
import shared.RequestType;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class RecomandedClassroomList extends TakingClassroomList implements MainPagePanel {
    public RecomandedClassroomList(TakingClassroomList tickedClassroomList) {
        super(tickedClassroomList);
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
        return RequestType.GET_REOCAMANDED_CLASSROOM;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
        if (!data.isEmpty()) {
            Set<ClassroomData> classroomData = data.stream().
                    map(x -> gson.fromJson(x, ClassroomData.class)).collect(Collectors.toSet());
            removeClassrooms(classroomData);
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

    private void removeClassrooms(Set<ClassroomData> classroomData) {
        Set<Long> ClassroomDataSet = new TreeSet<>(classroomData.stream().
                map(x -> x.getId()).
                collect(Collectors.toSet()));
        Set<Long> oldClassrooms = getRowsId().stream()
                .filter(x -> x != 0 && !ClassroomDataSet.contains(x)) //no topic
                .collect(Collectors.toSet());
        oldClassrooms.forEach(x -> removeRow(x));
    }
}
