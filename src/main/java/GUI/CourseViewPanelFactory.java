package GUI;

import shared.CourseViewData;

import javax.swing.*;
import java.util.ArrayList;

public class CourseViewPanelFactory {
    ArrayList<CourseViewPanel> courses;
    private JPanel out;
    private JPanel courseViewPanel;

    public CourseViewPanelFactory(JPanel out, JPanel courseViewPanel) {
        this.out = out;
        this.courseViewPanel = courseViewPanel;
        this.courses = new ArrayList<>();
    }

    public int build(String name) {
        courses.add(new CourseViewPanel(name, out));
        return courses.size() - 1;
    }

    public void enterPage(int index) {
        courseViewPanel.add(courses.get(index).getPanel());
        courses.get(index).enterPage();
    }

    public void updateCourse(int index, CourseViewData data) {

    }
}
