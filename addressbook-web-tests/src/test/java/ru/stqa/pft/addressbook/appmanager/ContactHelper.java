package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void selectContact() {
        if (! isElementPresent(By.name("selected[]"))) {
            initContactCreation();
            fillContactForm(
                    new ContactData(
                            "Firstname",
                            "Middlename",
                            "Lastname",
                            "Address",
                            "MobilePhone",
                            "Email",
                            "Test1"),
                    true);
            submitContactCreation();
            goToHomePage();
        }
        click(By.name("selected[]"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void initContactModification() {
        if (! isElementPresent(By.xpath("//img[@alt='Edit']"))){
            initContactCreation();
            fillContactForm(
                    new ContactData(
                            "Firstname",
                            "Middlename",
                            "Lastname",
                            "Address",
                            "MobilePhone",
                            "Email",
                            "Test1"),
                    true);
            submitContactCreation();
            goToHomePage();
        }
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void goToHomePage() {
        click(By.linkText("home"));
    }
}
