package GUI;

import com.google.gson.Gson;
import shared.NotificationData;
import shared.RequestType;

import javax.swing.*;
import java.util.*;

public class NotificationsPage implements SpecialUserPage {
    private JPanel notifications;
    private JPanel notificationsList;
    private JPanel notificationView;
    private JPanel panel;
    Map<Integer, NotificationPanel> notificationPanels;
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
        return RequestType.GET_NOTIFICATIONS;
    }

    @Override
    public void update(ArrayList<String> data, Gson gson) throws Exception {
        NotificationData[] notifications = gson.fromJson(data.get(0), NotificationData[].class);
        Set<NotificationData> notificationDataSet = new HashSet<>(Arrays.asList(notifications));
        notificationPanels.keySet().stream()
                .filter(x -> !notificationDataSet.contains(x))
                .forEach(x -> notificationPanels.remove(x));
        for (int i = notifications.length - 1;i >= 0;--i) {
            if (!notificationPanels.containsKey(notifications[i].getId())) {
                addNewNotification(notifications[i]);
            }
        }
    }

    private void addNewNotification(NotificationData notification) {
        String show = String.format("%s write by: %s", notification.getTitle(), notification.getSourceUserName());
        JButton button = new JButton(show);
        button.addActionListener(e -> enterToSubPanel(notification.getId()));
    }

    @Override
    public void enterToSubPanel(int id) {
        notificationView.add(notificationPanels.get(id).getPanel());
        UserConstantInformation.getInstance().setOutButton("notifications",
                e -> exitToThisPage());
        SpecialUserPage.super.enterToSubPanel(id);
    }

    @Override
    public void exitToThisPage() {
        notificationView.removeAll();
        SpecialUserPage.super.exitToThisPage();
    }
}
