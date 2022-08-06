package GUI;

import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListDesigner implements MainPagePanel {
    private static ListDesigner instance;

    static {
        instance = new ListDesigner();
    }
    private JTabbedPane main;
    private JPanel panel;
    private JButton showButton;
    private JComboBox selectedFaculty;
    private JPanel facultyFilter;

    private JRadioButton sortRadio;
    private JPanel FilterByOther;
    private JPanel filterMenu;
    private JPanel programItems;
    private JPanel Filter;
    private JPanel subjectsListMenu;
    private JPanel subjectsList;
    private static OptionCentricText facultySelector = new OptionCentricText(OptionCentricText.OptionsFrom.Faculties);
    private static OptionCentricText programSelector = new OptionCentricText(OptionCentricText.OptionsFrom.Program);
    public ListDesigner() {
        main.addChangeListener(new SelectMenuHandler(this, panel));

        facultySelector = new OptionCentricText(OptionCentricText.OptionsFrom.Faculties);
        programSelector = new OptionCentricText(OptionCentricText.OptionsFrom.Program);

        facultyFilter.add(facultySelector, BorderLayout.CENTER);
        programItems.add(programSelector);

        showButton.addActionListener(e -> showSubjectsList());
    }

    public void showSubjectsList() {
        DemoList demoList = LOGIC.Filter.getInstance().doSubjectFilter(
                facultySelector.getSelectedIndex(),
                programSelector.getSelectedIndex(),
                sortRadio.isSelected());
        subjectsList.removeAll();
        subjectsList.add(demoList);
        subjectsListMenu.repaint();
        subjectsListMenu.revalidate();
    }

    public static ListDesigner getInstance() {
        return instance;
    }

    @Override
    public JPanel getPanel() {
      //  subjectsListMenu.removeAll();
        return panel;
    }

    @Override
    public void setExit() {
        ManePagePanelFactory.setOutButtonToExitToMainPage();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        //Todo
    }
}
