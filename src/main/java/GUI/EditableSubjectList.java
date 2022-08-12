package GUI;

import LOGIC.Logger;
import client.Client;
import shared.ClassroomData;
import shared.Program;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EditableSubjectList extends SubjectList {
    private Runnable showNewList;
    private String selectedFacultyName;
    private Program selectedProgram;
    private JButton adderButton = new JButton("add") {
        {
            addActionListener(e -> {
                Client.getSender().send(RequestType.ADD_DEFAULT_CLASSROOM,
                        UserConstantInformation.getInstance().getUserId(),
                        selectedFacultyName,
                        selectedProgram);
                showNewList.run();
            });
        }
    };
    public EditableSubjectList(Runnable showNewList, String selectedFacultyName, int selectedProgramIndex) {
        super();
        this.showNewList = showNewList;
        this.selectedFacultyName = selectedFacultyName;
        this.selectedProgram = Program.values()[selectedProgramIndex];
        getRow(0).addFirst(adderButton);
    }

    @Override
    public void addRow(long id, Object[] inRow) {
        RowPanel rowPanel = new RowPanel();
        rowPanel.addComponents(getRemoveButton(id), getEditButton(id));
        addRow(id, rowPanel);
        super.addRow(id, inRow);
    }

    private JButton getRemoveButton(long id) {
        return new JButton("remove") {
            {
                addActionListener(e -> Client.getSender().send(RequestType.REMOVE_CLASSROOM,
                        id));
            }
        };
    }

    private JButton getEditButton(long id) {
        JButton button = new JButton("edit");
        button.addActionListener(e -> {
            if (button.getText().equals("edit")) {
                editSubjectRow(id);
                button.setText("send");
            } else {
                tryReadAndSend(id);
                button.setText("edit");
            }
        });
        return button;
    }

    private void editSubjectRow(long id) {
        RowPanel subjectRow = getRow(id);
        for (int i = 2;i < subjectRow.length();++i) {
            subjectRow.getCellPane(i).setEditable();
        }
    }
    private void tryReadAndSend(long id){
        try {
            RowPanel subjectRow = getRow(id);
            //Scanner reader = new Scanner();
            ArrayList<Integer>[] requisite = readRequisite(subjectRow.getText(5));
            Client.getInstance().send(RequestType.EDIT_CLASSROOM,
                    new ClassroomData(id, subjectRow, requisite[0], requisite[1]));
        } catch (Exception error) {
            System.out.println("not valid Input");
            Logger.logInfo( (this) + "try edit with not valid input");
        }
        //finish edit and add new edit button for try again
    }
    private ArrayList<Integer>[] readRequisite(String text) {
        Scanner reader = new Scanner(text);
        ArrayList<Integer> prerequisite = new ArrayList<>();
        ArrayList<Integer> coRequisite = new ArrayList<>();

        if (reader.next().equals("prerequisite:")) {
            while (reader.hasNextInt()) {
                prerequisite.add(reader.nextInt());
            }
            if (reader.next().equals("co-requisite:")) {
                while (reader.hasNextInt()) {
                    coRequisite.add(reader.nextInt());
                }
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
        return new ArrayList[]{prerequisite, coRequisite};
    }
}
