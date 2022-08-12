package database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notification")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.ORDINAL)
    private NotificationType type;
    @Column
    private String title;
    @Column
    private long senderUserId;
    @Column
    private String text;
    @Column
    private String senderName;

    public Notification() {
    }

    public Notification(NotificationType type, String title, long senderUserId, String text, String senderName) {
        this.type = type;
        this.title = title;
        this.senderUserId = senderUserId;
        this.text = text;
        this.senderName = senderName;
    }

    public Integer getId() {
        return id;
    }
    public NotificationType getType() {
        return type;
    }
    public String getTitle() {
        return title;
    }
    public long getSenderUserId() {
        return senderUserId;
    }
    public String getText() {
        return text;
    }
    public String getSenderName() {
        return senderName;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSenderUserId(long senderUserId) {
        this.senderUserId = senderUserId;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", senderUserId=" + senderUserId +
                ", text='" + text + '\'' +
                ", senderName='" + senderName + '\'' +
                '}';
    }

    public boolean isInfo() {
        return getType() == NotificationType.INFO;
    }

    public boolean isEditable() {
        return getType() == NotificationType.RECOMMENDATION;
    }
}