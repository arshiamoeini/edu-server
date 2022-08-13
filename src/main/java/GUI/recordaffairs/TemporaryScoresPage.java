package GUI.recordaffairs;

import GUI.*;
import com.google.gson.Gson;
import shared.ClassroomTemporaryRate;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TemporaryScoresPage implements MainPagePanel {
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JPanel records;
    private TemporaryScoresList scoresList;
    public TemporaryScoresPage() {
        scoresList = new TemporaryScoresList();
        records.add(scoresList.getListPanel());
        /*
        records.add(new DemoList() {
            {
                //TODO not clean
                columnsTitle = new String[] {"course name", "teacher name", "recorded scores", "send", "register a protest", "see answer"};
                designTopics();
                for (ClassroomTemp.classRating rating: Command.getInstance().getRegisteredRecords()) {
                    addRow(rating);
                }
            }

            private void addRow(ClassroomTemp.classRating rating) {
                gbcFiller.gridy = rowsCounter++;
                columnCounter = 0;
                addCopyableTextInRow(rating.getClassroom().getCourse().getName());
                addCopyableTextInRow(rating.getClassroom().getProfessorName());
                addCopyableTextInRow(String.valueOf(rating.getScore()));
                JButton sendButton = new JButton("send");
                gbcFiller.gridx = columnCounter++;
                pane.add(sendButton, gbcFiller);
                CellPane text = addCopyableTextInRowForEdit(rating.getProtest());
                text.setEditable();
                addCopyableTextInRow(rating.getAnswer());

                sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rating.setProtest(text.getText());
                    }
                });
            }
        });*/

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
        return RequestType.GET_TEMPORARY_SCORES;
    }

    @Override
    public synchronized void update(ArrayList<String> data, Gson gson) throws Exception {
        Set<ClassroomTemporaryRate> rates = data.stream().
                map(x -> gson.fromJson(x, ClassroomTemporaryRate.class)).
                collect(Collectors.toSet());
        removeOldRates(rates);
        updateRates(rates);
        records.repaint();
        records.revalidate();
    }

    private void updateRates(Set<ClassroomTemporaryRate> rates) {
        for (ClassroomTemporaryRate rate: rates) {
            if (!scoresList.contain(rate.getId())) {
                addNewRate(rate);
            } else {
                updateRate(rate);
            }
        }
    }

    private void updateRate(ClassroomTemporaryRate rate) {
        scoresList.updateRow(rate.getId(),
                rate.getRecordedScore(),
                rate.getSeeAnswer());
    }

    private void addNewRate(ClassroomTemporaryRate rate) {
        scoresList.addNew(
                rate.getCourseName(),
                rate.getTeacherName(),
                rate.getRecordedScore(),
                rate.getRegisterAProtest(),
                rate.getSeeAnswer(),
                rate.getId());
    }

    private void removeOldRates(Set<ClassroomTemporaryRate> rates) {
        Set<Long> ratesDataSet = new TreeSet<>(rates.stream().map(x -> x.getId()).collect(Collectors.toSet()));
        Set<Long> oldRates = scoresList.getRowsId().stream()
                .filter(x -> !ratesDataSet.contains(x)).collect(Collectors.toSet());
        oldRates.forEach(x -> scoresList.removeRow(x));
    }
}
