package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangePasswordHelper extends HelperBase {


  public ChangePasswordHelper(ApplicationManager app) {
    super(app);
  }

  public void clickUsersManage(){
    click(By.xpath("//span[text()=' управление ']"));
    click(By.xpath("//a[text()='Управление пользователями']"));
  }

  public void initPasswordReset() {
    wd.get(app.getProperty("web.baseUrl") + "manage_user_page.php");
  }

  public void start(String username, String password) {
    wd.get(app.getProperty("web.baseUrl")+"/login_page.php");
    type(By.name("username"),username);
    click(By.xpath("//input[@type='submit']"));
    type(By.name("password"),password);
    click(By.xpath("//input[@type='submit']"));
  }

  public void clickUser(String user) {
    String xpath = "//a[text()='"+user+"']";
    click(By.xpath(xpath));
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public String findResetLink(List<MailMessage> mailMessages, String email){
    MailMessage message = mailMessages.stream().filter((m)->m.to.equals("Someone (presumably you) requested a password ")).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(message.text);
  }

  public String findChangePasswordLink(List<MailMessage> mailMessages, String email){
    ArrayList<Long> datesList = new ArrayList<>();
    for (int i=0;i<mailMessages.size();i++) {
      datesList.add(mailMessages.get(i).messageDate.getTime());
    }
    long max = Collections.max(datesList);
    int latestIndex = 0;
    for (int i=0;i<mailMessages.size();i++) {
      if (mailMessages.get(i).messageDate.getTime()==max) {
        latestIndex = i;
        break;
      }
    }
    MailMessage message = mailMessages.get(latestIndex);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(message.text);
  }

  public void finish(String link, String pass) throws ClassNotFoundException {
    wd.get(link);
    type(By.name("password"),pass);
    type(By.name("password_confirm"),pass);
    click(By.xpath("//span[text() = 'Изменить учетную запись']"));
  }
}