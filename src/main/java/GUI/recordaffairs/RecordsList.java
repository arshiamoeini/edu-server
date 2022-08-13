package GUI.recordaffairs;

import GUI.DemoSet;
import GUI.MainPagePanel;
import GUI.RowPanel;
import client.Client;
import com.google.gson.Gson;
import shared.ClassroomRatingData;
import shared.RequestType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RecordsList extends DemoSet implements MainPagePanel {
    private JPanel panel3;
    private JPanel titles;
    protected JPanel operation;
    private JPanel list;
    protected long id;
    private boolean editable;
    private Runnable exit;

    public RecordsList(long id, boolean editable, Runnable exit) {
        this.id = id;
        this.editable = editable;
        this.exit = exit;
        list.add(super.getListPanel());
        if (editable) {
            operation.add(getRegisterButton());
        }
        designTopics();
    }

    private JButton getRegisterButton() {
        return new JButton("register") {
            {
                addActionListener(e -> {
                    if (editRates(getElements().stream().
                            filter(x -> x.getKey() != 0).
                            collect(Collectors.toList()))) {
                        Client.getSender().send(RequestType.REGISTER_CLASSROOM, id);
                        destroyAll();
                    }
                });
            }
        };
    }

    protected void destroyAll() {
        exit.run();
    }

    private boolean editRates(List<Map.Entry<Long, RowPanel>> rates) {
        for (Map.Entry<Long, RowPanel> rate: rates) {
            RowPanel rowPanel = rate.getValue();
            try {
                sendEdits(rate.getKey(), rowPanel.getText(2), null);
            } catch (Exception ex) {
                System.out.println("not valid score");
                return false;
            }
        }
        return true;
    }


    @Override
    public JPanel getTitles() {
        return titles;
    }

    @Override
    public JPanel getPanel() {
        return operation;
    }

    @Override
    public void setExit() {
    }

    @Override
    public RequestType getUpdateRequest() {
        Client.getSender().send(RequestType.GET_SCORES_OF_CLASSROOM,
                id);
        return null;
    }

    @Override
    public synchronized void update(ArrayList<String> data, Gson gson) throws Exception {
        Set<ClassroomRatingData> ratingData = data.stream().
                map(x -> gson.fromJson(x, ClassroomRatingData.class)).
                collect(Collectors.toSet());
        updateRates(ratingData);
        list.repaint();
        list.revalidate();
    }

    private void updateRates(Set<ClassroomRatingData> ratingData) {
        for (ClassroomRatingData rating: ratingData) {
            if (!this.contain(rating.getId())) {
                addNewRate(rating);
            } else {
                if (!editable) {
                    updateRate(rating);
                }
            }
        }
    }

    protected void updateRate(ClassroomRatingData rating) {
        RowPanel rowPanel = getRow(rating.getId());
        rowPanel.getCellPane(2).setText(String.valueOf(rating.getScore()));
        rowPanel.getCellPane(3).setText(rating.getProtest());
        //    rowPanel.getCellPane(protestRowIndex).setText(registerAProtest);
        rowPanel.getCellPane(4).setText(rating.getAnswer());
    }

    protected void addNewRate(ClassroomRatingData rating) {
        RowPanel rowPanel = new RowPanel();
        rowPanel.addCopyableDataInRow(rating.getStudentName(), rating.getStudentId(), rating.getScore(), rating.getProtest(), rating.getAnswer());
        if (editable) rowPanel.getCellPane(2).setEditable();
        addRow(rating.getId(), rowPanel);
    }
    protected void sendEdits(Long id, String scoreStr, String answer) throws Exception {
        double score = tryReadScore(scoreStr);
        Client.getSender().send(RequestType.EDIT_RATING, new ClassroomRatingData(id, score, answer));
        removeRow(id);
    }
    protected double tryReadScore(String scoreStr) throws Exception {
        double scoreNumber = Double.valueOf(scoreStr);
        if (0 <= scoreNumber && scoreNumber <= 20) {
            return scoreNumber;
        } else {
            throw new RuntimeException();
        }
    }

    public Long getId() {
        return id;
    }
}
