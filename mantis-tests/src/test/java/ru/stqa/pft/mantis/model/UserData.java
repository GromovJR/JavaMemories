package ru.stqa.pft.mantis.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@XStreamAlias("user")
@Entity
@Table(name = "mantis_user_table")
public final class UserData {

  @XStreamOmitField
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "email")
  private String email;

  @Expose
  @Column(name = "username")
  private String username;

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData withUsername(String username) {
    this.username = username;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return id == userData.id && Objects.equals(email, userData.email) && Objects.equals(username, userData.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, username);
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            '}';
  }
}
