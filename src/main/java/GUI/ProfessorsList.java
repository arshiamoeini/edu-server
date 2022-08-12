package GUI;

import LOGIC.Logger;
import client.Client;
import com.google.gson.Gson;
import shared.ProfessorData;
import shared.RequestType;
import shared.UserType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ProfessorsList extends DemoSet implements MainPagePanel {
    private JPanel pane;
    private JPanel titles;
    private JPanel facultyFilter;
    private JPanel list;
    private JButton showButton;
    private static OptionCentricText facultySelector = new OptionCentricText(OptionCentricText.OptionsFrom.Faculties);

    private BiConsumer<Long, Object[]> addNotEditableRow = (id, inRow) -> {
        if (!contain(id)) addRow(id, new RowPanel());
        RowPanel rowPanel = getRow(id);
        for (int i = 0; i < inRow.length; i++) {
            rowPanel.addCopyableDataInRow(inRow[i]);
        }
    };
    private BiConsumer<Long, Object[]> addEditableRow = (id, inRow) -> {
        RowPanel rowPanel = new RowPanel();
        rowPanel.addComponents(getEditButton(id), getRemoveButton(id));
        addRow(id, rowPanel);
        addNotEditableRow.accept(id, inRow);
    };
    private BiConsumer<Long, Object[]> addRowStrategy;
    private String selectedFacultyName;
    private JButton adderButton = new JButton("add") {
        {
            addActionListener(e -> {
                Client.getSender().send(RequestType.ADD_DEFAULT_CLASSROOM,
                        UserConstantInformation.getInstance().getUserId(),
                        selectedFacultyName);
                showProfessorsList();
            });
        }
    };
    public ProfessorsList() {
        facultyFilter.add(facultySelector);
        showButton.addActionListener(e -> showProfessorsList());
    }

    private boolean canEditUser() {
        return true;// UserConstantInformation.getInstance().userType == UserType.CampusChairmen;//TODO Nessesery
    }

    private void showProfessorsList() {
        selectedFacultyName = facultySelector.getName();
        super.removeAll();
        designTopics();
        addRowStrategy = canEditUser() ? addNotEditableRow : addEditableRow;
        list.add(getListPanel());
        Client.getSender().send(RequestType.GET_PROFESSORS_OF_SELECTED_FACULTY,
                facultySelector.getSelectedItemName());
    }

    @Override
    protected void designTopics() {
        super.designTopics();
        if (canEditUser()) {
            getRow(0).addFirst(adderButton);
        }
    }

    private JButton getRemoveButton(Long id) {
        return new JButton("remove") {
            {
                addActionListener(e -> Client.getSender().send(RequestType.REMOVE_PROFESSOR,
                        id));
            }
        };
    }

    private JButton getEditButton(Long id) {
        JButton button = new JButton("edit");
        button.addActionListener(e -> {
            if (button.getText().equals("edit")) {
                editProfessorRow(id);
                button.setText("send");
            } else { //send strategy
                tryReadAndSend(id);
                button.setText("edit");
            }
        });
        return button;
    }

    private void editProfessorRow(Long id) {
        RowPanel professorRow = getRow(id);
        for (int i = 2;i < professorRow.length();++i) {
            professorRow.getCellPane(i).setEditable();
        }
    }

    private void tryReadAndSend(long id) {
        try {
            RowPanel professorRow = getRow(id);
            //Scanner reader = new Scanner();
            Client.getInstance().send(RequestType.EDIT_PROFESSOR,
                    new ProfessorData(professorRow),
                    id);
        } catch (Exception error) {
            System.out.println("not valid Input");
            Logger.logInfo((this) + "try edit with not valid input");
        }
    }

    @Override
    public JPanel getTitles() {
        return titles;
    }

    @Override
    public JPanel getPanel() {
        return pane;
    }

    @Override
    public void setExit() {
    }

    @Override
    public RequestType getUpdateRequest() {
        return RequestType.CHECK_CONNECTION;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
        if (!data.isEmpty()) {
            ProfessorData[] professorData = gson.fromJson(data.get(0), ProfessorData[].class);
            /*data.stream().
                    map(x -> gson.fromJson(x, ProfessorData.class)).
                    toArray(ProfessorData[]::new);*/

            removeProfessors(professorData);
            addProfessors(professorData);
            list.repaint();
            list.revalidate();
        }
    }

    private void addProfessors(ProfessorData[] professorData) {
        for(ProfessorData data: professorData) if(!contain(data.getId())) {
            addRowStrategy.accept(data.getId(), data.getInRow());
        }
    }

    private void removeProfessors(ProfessorData[] professorData) {
        Set<Long> ProfessorDataSet = new TreeSet<>(Arrays.stream(professorData).
                map(x -> x.getId()).
                collect(Collectors.toSet()));
        Set<Long> oldProfessors = getRowsId().stream()
                .filter(x -> x != 0 && !ProfessorDataSet.contains(x))
                .collect(Collectors.toSet());
        oldProfessors.forEach(x -> remove(x));
    }
}
