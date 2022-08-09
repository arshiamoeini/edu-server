package GUI;

import com.google.gson.Gson;
import javafx.collections.transformation.SortedList;
import shared.NotificationData;
import shared.RequestType;

import javax.swing.*;
import java.security.KeyStore;
import java.util.*;
import java.util.stream.Collectors;

public class NotificationsPage implements SpecialUserPage {
    private JPanel notifications;
    private JPanel notificationsButtonList;
    private JPanel notificationView;
    private JPanel panel;
    Map<Integer, NotificationPanel> notificationPanels;

    public NotificationsPage() {
        notificationPanels = new HashMap<>();
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
        return RequestType.GET_NOTIFICATIONS;
    }

    @Override
    public synchronized void update(ArrayList<String> data, Gson gson) throws Exception {
        NotificationData[] notifications = gson.fromJson(data.get(0), NotificationData[].class);
        removeOldNotifications(notifications);
        for (NotificationData notification: notifications) { //new notification is in end of the list
            if (!notificationPanels.containsKey(notification.getId())) {
                addNewNotification(notification);
            }
        }
    }

    private void removeOldNotifications(NotificationData[] notifications) {
        //sorted
        Set<Integer> notificationDataSet = new TreeSet<>(Arrays.stream(notifications).map(x -> x.getId()).collect(Collectors.toSet()));
        Set<Integer> oldNotification = notificationPanels.keySet().stream()
                .filter(x -> !notificationDataSet.contains(x)).collect(Collectors.toSet()); //the oldNotification is equals to notificationPanel \ notificationDataSet
        oldNotification.forEach(x -> removeNotification(x));
    }

    private void removeNotification(int id) {
        notificationsButtonList.remove(notificationPanels.get(id).getEnterButton());
        notificationPanels.remove(id);
    }

    private void addNewNotification(NotificationData notification) {
        JButton button = addEnterButtonToNotification(notification);
        NotificationPanel notificationPanel = (notification.isInfo() ?
                new NotificationPanel(button, notification.getText()) :
                new NotificationPanel(button, notification.isEditable(), notification.getId(), notification.getText(), notification.getSourceUserId()));
        notificationPanels.put(notification.getId(), notificationPanel);
    }

    private JButton addEnterButtonToNotification(NotificationData notification) {
        String show = String.format("%s write by: %s", notification.getTitle(), notification.getSourceUserName());
        JButton button = new JButton(show);
        button.addActionListener(e -> enterToSubPanel(notification.getId()));
        notificationsButtonList.add(button, 0); //insert to first place
        return button;
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
