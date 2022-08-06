package GUI;

import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class CourseViewPanel implements Updatable {
    private JPanel panel;
    private JPanel addStudentField;
    private JPanel calender;
    private JPanel addExercisesButton;
    private JPanel exercises;
    private JPanel addMaterialsButton;
    private JPanel educationalMaterials;
    private JLabel nameLabel;
    private JPanel out;

    public CourseViewPanel(String name, JPanel out) {
        this.out = out;
        nameLabel.setText(name);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setExit() {
        //it will handel on the Courseware::enterCourseViewPanel
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
    }
}
