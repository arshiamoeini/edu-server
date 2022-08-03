package database;

import javax.persistence.*;
import java.io.Serializable;

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

    public User() {
    }

    public User(long id, String password) {
        this.id = id;
        setPassword(password);
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

    public void setId(long id) {
        this.id = id;
    }
    public void setHashOfPassword(int hashOfPassword) {
        this.hashOfPassword = hashOfPassword;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", hashOfPassword=" + hashOfPassword +
                ", name='" + name + '\'' +
                '}';
    }
}
