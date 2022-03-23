package ru.stqa.pft.mantis.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.DbHelper;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.testng.AssertJUnit.assertTrue;

public class PasswordChangeTests extends TestBase{

    @Test
    public void changePassword() throws IOException, ClassNotFoundException {
      List<UserData> users = new DbHelper().users();
      int index = 0;
      do{
        index = new Random().nextInt(users.size());
      } while (users.get(index).getUsername().equals("administrator"));
      String email = users.get(index).getEmail();
      String username = users.get(index).getUsername();

      app.changePass().start("administrator","root");
      app.changePass().initPasswordReset();
      app.changePass().clickUser(username);
      app.changePass().resetPassword();
      List<MailMessage> mailMessages = app.mail().waitForMail(2, 60000);
      String passwordChangeLink = app.changePass().findChangePasswordLink(mailMessages, email);
      String newPassword = "password1";
      app.changePass().finish(passwordChangeLink, newPassword);

      HttpSession session = app.newSession();

      assertTrue(session.login(username, newPassword));
      assertTrue(session.isLoggedInAs(username));
    }
}