package ru.stqa.pft.mantis.model;

import java.util.Date;

public class MailMessage {

  public String to;
  public String text;
  public Date messageDate;
  public MailMessage(String to, String text, Date messageDate) {
    this.to = to;
    this.text = text;
    this.messageDate = messageDate;
  }
}
