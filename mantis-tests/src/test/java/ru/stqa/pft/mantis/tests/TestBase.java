package ru.stqa.pft.mantis.tests;

import com.google.protobuf.ServiceException;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;


public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_defaults_inc.php"), "config_defaults_inc.php", "config_defaults_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config_defaults_inc.php.bak", "config_defaults_inc.php");
    app.stop();
  }


  public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException, javax.xml.rpc.ServiceException {
    if (app.soap().getIssueStatus(issueId).equals("resolved") || app.soap().getIssueStatus(issueId).equals("closed"))
    {
      return false;
    } else{
      return true;
    }
  }

  public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException, javax.xml.rpc.ServiceException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }


}
