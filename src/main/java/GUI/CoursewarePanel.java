package GUI;

import com.google.gson.Gson;
import javafx.scene.control.ChoiceBox;
import shared.CourseViewData;
import shared.RequestType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoursewarePanel implements SpecialUserPage {
    private JPanel panel;
    private JPanel courseList;
    private JPanel calender;
    private JPanel courses;
    private JPanel courseViewPanel;
    private Map<Integer, Integer> courseKey;
    private final CourseViewPanelFactory factory;
    private JPanel out;

    public CoursewarePanel(JPanel out) {
        this.out = out;
        factory = new CourseViewPanelFactory(out, courseViewPanel);
        courseKey = new HashMap<>();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setExit() {
        ManePagePanelFactory.setOutButtonToExitToMainPage();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.GET_COURSE_VIEWS;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {//Debug
       // List<String> st = Arrays.stream(gson.fromJson(data[0], String[].class)).collect(Collectors.toList());
       // List<CourseViewData> courseViewsData = Arrays.stream(gson.fromJson(data[0], String[].class)).map(x -> gson.fromJson(x, CourseViewData.class)).collect(Collectors.toList());
        CourseViewData[] courseViewsData = gson.fromJson(data.get(0), CourseViewData[].class);
        Arrays.stream(courseViewsData).
                filter(x -> !courseKey.containsKey(x.getID())).
                forEach(x -> addNewCourseView(x));
        Arrays.stream(courseViewsData).
                forEach(x -> factory.updateCourse(courseKey.get(x.getID()), x));
    }

    private void addNewCourseView(CourseViewData courseViewData) {
        int index = factory.build(courseViewData.getName());
        courseKey.put(courseViewData.getID(), index);
        JButton button = new JButton(courseViewData.getName());
        button.addActionListener(e -> enterToSubPanel(index));
        courseList.add(button);
        panel.repaint();
        panel.revalidate();
    }
    @Override
    public void enterToSubPanel(int id) {
        factory.enterPage(id);
        UserConstantInformation.getInstance().setOutButton("courseware",
                ee -> exitToThisPage());
        SpecialUserPage.super.enterToSubPanel(id);
    }
    @Override
    public void exitToThisPage() {
        courseViewPanel.removeAll();
        SpecialUserPage.super.exitToThisPage();
    }
}
