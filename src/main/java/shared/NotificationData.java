package shared;

import database.Notification;
import database.NotificationType;

public class NotificationData implements Comparable<NotificationData> {
    private int id;
    private String title;
    private String sourceUserName;
    private String text;
    private long sourceUserId;
    private boolean info;
    private boolean editable;
    public NotificationData(int id, String title, String sourceUserName, String text, long sourceUserId) {
        this.id = id;
        this.title = title;
        this.sourceUserName = sourceUserName;
        this.text = text;
        this.sourceUserId = sourceUserId;
    }

    public NotificationData(Notification nf, boolean info, boolean editable) {
        this(nf.getId(), nf.getTitle(), nf.getSenderName(), nf.getText(), nf.getSenderUserId());
        this.info = info;
        this.editable = editable;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getSourceUserName() {
        return sourceUserName;
    }
    public String getText() {
        return text;
    }
    public long getSourceUserId() {
        return sourceUserId;
    }
    public boolean isInfo() {
        return info;
    }

    @Override
    public int compareTo(NotificationData o) {
        if (o == null) return 1;
        if (id > o.getId()) return 1;
        else if (id == o.getId()) return 0;
        return -1;
    }

    public boolean isEditable() {
        return editable;
    }
}
