package GUI.REQUEST_MENU;

import GUI.*;
import LOGIC.Command;
import MODELS.MajorRequestTemp;
import client.Client;
import com.google.gson.Gson;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;

public class BachelorStudentRequestsMenu implements RequestMenuPanel {
    private JTabbedPane bachelorStudent;
    private JPanel panel;
    private JButton certificateButton;
    private JTextArea certificateText;
    private JButton addMajor;
    private JPanel applyMajored;
    private JPanel majorList;
    private JButton outOfUniversity;
    private JPanel majorMenu;
    private JPanel certificateMenu;
    private JPanel recommendationMenu;
    private JPanel withdrawalMenu;
    CertificateMenu certificateMenuDesigner;
    public BachelorStudentRequestsMenu() {
        bachelorStudent.addChangeListener(new SelectMenuHandler(this, panel));
        recommendationInit();
        certificateInit();
        withdrawalInit();
        majorInit();
    }


    private void recommendationInit() {
        RecommendationMenu recommendationMenu = new RecommendationMenu();
        getSelectMenuHandler().addUpdatable(recommendationMenu);
        this.recommendationMenu.add(recommendationMenu.getPanel());
        /*recommendationList.add(new DemoList() {
            {
                columnsTitle = new String[]{"click to show recommendation", "text area"};
                designTopics();
                for (String recommendationText: Command.getInstance().getRecommendationsText()) {
                    addRow(recommendationText);
                }
            }
            private void addRow(String recommendationText) {
                gbcFiller.gridy = rowsCounter++;

                JButton button = new JButton();
                gbcFiller.gridx = columnCounter++;
                pane.add(button, gbcFiller);
                CellPane textArea = addCopyableTextInRowForEdit("");

                button.addActionListener(e -> ((JTextArea) textArea.getLabel()).setText(recommendationText));
            }
        });*/
    }
    private void certificateInit() {
        certificateMenuDesigner = new CertificateMenu();
        certificateMenu.add(certificateMenuDesigner.getPanel());
    }
    private void withdrawalInit() {
        withdrawalMenu.add(new WithdrawalMenu().getPanel());
    }
    private void majorInit() {
        applyMajored.add(new OptionCentricText(OptionCentricText.OptionsFrom.Faculties));
        addMajor.addActionListener(e -> {
            Client.getInstance().send(RequestType.ADD_MAJOR_REQUEST,
                    ((OptionCentricText) applyMajored.getComponent(1)).getSelectedItemName());
        });
    }
    private void updateMajorList(String[] majoredFacultiesName, String[] majorsStatus) {
        majorList.removeAll();
        majorList.add(new DemoList() {
            {
                columnsTitle = new String[] {"faculty name", "status"};
                designTopics();
                for (int i = 0; i < majorsStatus.length; i++) {
                    addRow(majoredFacultiesName[i], majorsStatus[i]); //TODO Config
                }
            }
            public void addRow(String facultyName, String status) {
                gbcFiller.gridy = rowsCounter++;
                columnCounter = 0;
                addCopyableTextInRow(facultyName);
                addCopyableTextInRow(status);
            }
        });
        majorMenu.revalidate();
    }
    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public JTabbedPane getRootPage() {
        return bachelorStudent;
    }

    @Override
    public void setExit() {
        ManePagePanelFactory.setOutButtonToExitToMainPage();
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.GET_MAJORS_STATUS;
    }

    @Override
    public synchronized void update(ArrayList<String> data, Gson gson) throws Exception {
        String[] majoredFacultiesName = gson.fromJson(data.get(0), String[].class);
        String[] majorsStatus = gson.fromJson(data.get(1), String[].class);
        updateMajorList(majoredFacultiesName, majorsStatus);
    }
}
