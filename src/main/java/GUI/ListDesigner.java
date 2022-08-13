package GUI;

import GUI.requestmenu.SelectRequestMenuHandler;
import client.Client;
import com.google.gson.Gson;
import shared.ClassroomData;
import shared.RequestType;
import shared.UserType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ListDesigner implements NormalUserPage {
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
    private JPanel listOfSubjects;
    private JPanel professorList;
    private JRadioButton sortByCourseName;
    private OptionCentricText facultySelector = new OptionCentricText(OptionCentricText.OptionsFrom.Faculties);
    private OptionCentricText programSelector = new OptionCentricText(OptionCentricText.OptionsFrom.Program);
    private SubjectList subjectList;
    public ListDesigner() {
        main.addChangeListener(new SelectRequestMenuHandler(this, panel));
        professorListInit();
        subjectListInit();
        Filter.setVisible(false);
    }

    private void subjectListInit() {
        facultyFilter.add(facultySelector);
        programItems.add(programSelector);
        showButton.addActionListener(e -> showSubjectsList());
    }

    private void professorListInit() {
        ProfessorsList professorsList = new ProfessorsList();
        getSelectMenuHandler().addUpdatable(2, professorsList);
        this.professorList.add(professorsList.getPanel());
    }

    public void showSubjectsList() {
        listOfSubjects.removeAll();
        subjectList = canEditClassroom() ? getEditableClassroomList()
                 : new SubjectList();
        listOfSubjects.add(subjectList.getListPanel());
        sendRequestToGetFilteredResult();
    }

    private EditableSubjectList getEditableClassroomList() {
        return new EditableSubjectList(this::showSubjectsList,
                facultySelector.getSelectedItemName(),
                programSelector.getSelectedIndex());
    }

    private void sendRequestToGetFilteredResult() {
        Client.getSender().send(RequestType.GET_ClASS_ROOM_WITH_FILTER,
                sortByCourseName.isSelected(),
                sortRadio.isSelected(),
                programSelector.getSelectedIndex(),
                facultySelector.getSelectedItemName());
    }

    private boolean canEditClassroom() {
        return UserConstantInformation.getInstance().userType == UserType.EducationalAssistant
                && UserConstantInformation.getInstance().getFacultyName().equals(
                        facultySelector.getSelectedItemName());
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
    public JTabbedPane getRootPage() {
        return main;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) {
        if (!data.isEmpty()) {
            ClassroomData[] classroomData = data.stream().
                    map(x -> gson.fromJson(x, ClassroomData.class)).
                    toArray(ClassroomData[]::new);
            removeClassrooms(classroomData);
            addClassrooms(classroomData);
            subjectsListMenu.repaint();
            subjectsListMenu.revalidate();
        }
    }

    private void addClassrooms(ClassroomData[] classroomData) {
        for (ClassroomData data: classroomData) if(!subjectList.contain(data.getId())) {
            subjectList.addRow(data.getId(), data.getInRow());
        }
    }

    private void removeClassrooms(ClassroomData[] classroomData) {
        Set<Long> ClassroomDataSet = new TreeSet<>(Arrays.stream(classroomData).
                map(x -> x.getId()).
                collect(Collectors.toSet()));
        Set<Long> oldClassrooms = subjectList.getRowsId().stream()
                .filter(x -> x != 0 && !ClassroomDataSet.contains(x)) //no topic
                .collect(Collectors.toSet());
        oldClassrooms.forEach(x -> subjectList.removeRow(x));
    }
}
