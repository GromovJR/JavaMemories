package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void select(int index) {
        wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input")).get(index).click();
       // click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
    }

    public void selectById(int id) {
        wd.findElement(By.cssSelector("input[value= '" + id + "']")).click();
    }

    public void deleteSelected() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void initModification() {
        //wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
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

    public int getCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void create(ContactData contact, boolean creation) {
        initCreation();
        fillForm(
                contact,
                creation);
        submitCreation();
        contactCache = null;
        goToHomePage();
    }

    public void modify(ContactData contactData, boolean creation) {
        initModificationById(contactData.getId());
        fillForm(contactData, creation);
        submitModification();
        contactCache = null;
        goToHomePage();
    }


    public void delete(ContactData contactData) {
        selectById(contactData.getId());
        deleteSelected();
        contactCache = null;
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
            ContactData contact = new ContactData()
                    .withId(id)
                    .withFirstname(firstname)
                    .withLastname(lastname);
            contacts.add(contact);
        }
        return contacts;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String Address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            ContactData contact = new ContactData()
                    .withId(id).withFirstname(firstname).withLastname(lastname)
                    .withAddress(Address).withAllEmails(allEmails).withAllPhones(allPhones);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData()
                .withId(contact.getId()).withFirstname(firstname)
                .withLastname(lastname).withHomePhone(home)
                .withMobilePhone(mobile).withWorkPhone(work);

    }

    private void initModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }
}
