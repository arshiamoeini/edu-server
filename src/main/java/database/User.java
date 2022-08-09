package database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int hashOfPassword;
    @Column
    String name;
    @OneToMany
    @OrderBy("id")
    List<Notification> notifications;
    public User() {
    }

    public User(long id, String password) {
        this.id = id;
        setPassword(password);
        notifications = new ArrayList<>();
    }

    public void setPassword(String password) { hashOfPassword = password.hashCode(); }

    public long getId() {
        return id;
    }
    public int getHashOfPassword() {
        return hashOfPassword;
    }
    public String getName() {
        return name;
    }
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setHashOfPassword(int hashOfPassword) {
        this.hashOfPassword = hashOfPassword;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", hashOfPassword=" + hashOfPassword +
                ", name='" + name + '\'' +
                '}';
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }
}
