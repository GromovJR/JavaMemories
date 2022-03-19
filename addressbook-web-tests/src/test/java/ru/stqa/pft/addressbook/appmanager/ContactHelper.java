package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initCreation() {
        click(By.linkText("add new"));
    }

    public void fillForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
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

    public void submitCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void select() {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
    }

    public void deleteSelected() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void initModification() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void submitModification() {
        click(By.name("update"));
    }

    public void goToHomePage() {
        click(By.linkText("home"));
    }

    public boolean tryToSearch() {
        return isElementPresent(By.name("selected[]"));

    }

    public void create(ContactData contact, boolean creation) {
        initCreation();
        fillForm(
                contact,
                creation);
        submitCreation();
        goToHomePage();
    }

    public void modify(ContactData contactData, boolean creation) {
        initModification();
        fillForm(contactData, creation);
        submitModification();
        goToHomePage();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            ContactData contact = new ContactData(
                    id,
                    firstname,
                    lastname,
                    null,
                    null,
                    null,
                    null);
            contacts.add(contact);
        }
        return contacts;
    }

    public void delete() {
        select();
        deleteSelected();
        goToHomePage();
    }
}
