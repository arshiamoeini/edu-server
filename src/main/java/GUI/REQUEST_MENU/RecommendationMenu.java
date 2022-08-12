package GUI.REQUEST_MENU;

import GUI.OptionCentricText;
import GUI.RequestMenuPanel;
import GUI.Updatable;
import GUI.UserConstantInformation;
import client.Client;
import client.Pulsator;
import com.google.gson.Gson;
import org.hibernate.event.spi.ClearEvent;
import shared.ProfessorData;
import shared.RequestType;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

public class RecommendationMenu implements PropertyChangeListener, Updatable {
    private JPanel recommendationMenu;
    private JPanel recommendationField;
    private JButton addRecommendation;
    private JPanel recommendationList;
    private JPanel panel;
    private OptionCentricText professorSelector;
    private OptionCentricText facultySelector;

    public RecommendationMenu() {
        addFieldOfRecommendation();
        addRecommendation.addActionListener(e -> {
            if (professorSelector != null) {
                Client.getInstance().send(RequestType.ADD_RECOMMENDATION,
                        professorSelector.getSelectedIndex(),
                        facultySelector.getSelectedItemName(),
                        UserConstantInformation.getInstance().getUserId());
            }
        });
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

    private void addFieldOfRecommendation() {
        facultySelector = new OptionCentricText(OptionCentricText.OptionsFrom.Faculties);
        facultySelector.addPropertyChangeListener(this);
        recommendationField.add(facultySelector, BorderLayout.CENTER);
   //     professorSelector = new OptionCentricText(new Object[]{});
   //     recommendationField.add(professorSelector,
    //            BorderLayout.EAST);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (professorSelector == null) return;
        recommendationField.remove(professorSelector); //change faculty
        professorSelector = null;
        Client.getSender().send(RequestType.GET_PROFESSORS_OF_SELECTED_FACULTY,
                facultySelector.getSelectedItemName());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setExit() {
    }

    @Override
    public RequestType getUpdateRequest() {
       // if (professorSelector == null) {
       //     Pulsator.getInstance().setSelectedFacultyName();
         ///   return RequestType.GET_PROFESSORS_OF_SELECTED_FACULTY;
       // } else {
            return RequestType.CHECK_CONNECTION;
       // }
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
        if (!data.isEmpty()) {
            String[] professorsName = Arrays.stream(gson.fromJson(data.get(0), ProfessorData[].class)).
                    map(x -> x.getName()).toArray(String[]::new);
            professorSelector = new OptionCentricText(professorsName);
            recommendationField.add(professorSelector);
            panel.revalidate();
            panel.repaint();
        }
    }
}
