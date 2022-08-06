package shared;

public class NotificationData {
    private int id;
    private String title;
    private String sourceUserName;
    private String text;
    private long sourceUserId;

    public NotificationData(int id, String title, String sourceUserName, String text, long sourceUserId) {
        this.id = id;
        this.title = title;
        this.sourceUserName = sourceUserName;
        this.text = text;
        this.sourceUserId = sourceUserId;
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
}
