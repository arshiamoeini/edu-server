package GUI.takeclasses;

import GUI.ManePagePanelFactory;
import GUI.NormalUserPage;
import GUI.OptionCentricText;
import GUI.SubjectList;
import GUI.requestmenu.SelectRequestMenuHandler;
import client.Client;
import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class TakingClassesPage implements NormalUserPage {
    private JTabbedPane main;
    private JPanel panel;
    private JPanel Filter;
    private JPanel facultyFilter;
    private JPanel filterMenu;
    private JPanel FilterByOther;
    private JRadioButton sortRadio;
    private JPanel programItems;
    private JRadioButton sortByCourseName;
    private JButton showButton;
    private JPanel list;
    private JPanel recomandedList;
    private JPanel tickedList;
    private JPanel tackedClassroomPanel;
    private JPanel chosseGroup;
    private JPanel tackedClassroomList;
    private OptionCentricText facultySelector = new OptionCentricText(OptionCentricText.OptionsFrom.Faculties);
    private OptionCentricText programSelector = new OptionCentricText(OptionCentricText.OptionsFrom.Program);
    private SubjectList subjectList;
    private boolean okToUser = false;
    private RecomandedClassroomList recomandedClassroomList;
    private TakingClassroomList tickedClassroomList;
    public TakingClassesPage() {
        setAccssess(false);
        main.addChangeListener(new SelectRequestMenuHandler(this, panel));
        subjectListInit();
        takingClassesInit();
        tackedClassroomInit();
    }

    private void tackedClassroomInit() {
        TackedClassroomList classroomList = new TackedClassroomList(tickedClassroomList);
        addCartPanel(3, classroomList);
        tackedClassroomList.add(classroomList.getPanel());
    }

    private void takingClassesInit() {
        tickedClassroomList = new TakingClassroomList();
        recomandedClassroomList = new RecomandedClassroomList(tickedClassroomList);
        tickedList.add(tickedClassroomList.getListPanel());
        recomandedList.add(recomandedClassroomList.getListPanel());
        getSelectMenuHandler().addUpdatable(2, recomandedClassroomList);
    }


    private void subjectListInit() {
        facultyFilter.add(facultySelector);
        programItems.add(programSelector);
        showButton.addActionListener(e -> showSubjectsList());
    }
    public void showSubjectsList() {
        list.removeAll();
        subjectList = new TakingClassroomList();
        list.add(subjectList.getListPanel());
        sendRequestToGetFilteredResult();
    }

    private void sendRequestToGetFilteredResult() {
        Client.getSender().send(RequestType.GET_ClASS_ROOM_WITH_FILTER,
                sortByCourseName.isSelected(),
                sortRadio.isSelected(),
                programSelector.getSelectedIndex(),
                facultySelector.getSelectedItemName());
    }

    @Override
    public JTabbedPane getRootPage() {
        return main;
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
        return RequestType.CHECK_CAN_USER_TAKING_CLASS_PAGE;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
        boolean okToUser = Boolean.valueOf(data.get(0));
        if (okToUser != this.okToUser) {
            this.okToUser = okToUser;
            setAccssess(okToUser);
        }
    }

    private void setAccssess(boolean ok) {
        main.setVisible(ok);
        panel.repaint();
        panel.revalidate();
    }
}
