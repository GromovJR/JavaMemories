package ru.stqa.pft.rest;

import java.util.Objects;

public class Issue {

  private int id;
  private String subject;
  private String description;
  private int state;

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  public int getState() {
    return state;
  }

  public Issue withState(int state) {
    this.state = state;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id && Objects.equals(subject, issue.subject) && Objects.equals(description, issue.description) && Objects.equals(state, issue.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, subject, description, state);
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", description='" + description + '\'' +
            ", state='" + state + '\'' +
            '}';
  }

}
