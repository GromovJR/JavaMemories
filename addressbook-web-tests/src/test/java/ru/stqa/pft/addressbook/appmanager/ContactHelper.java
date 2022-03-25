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
        //Add photo to profile
        //attach(By.name("photo"), contactData.getPhoto());

        if (creation){
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }


    public ContactData selectById(int id) {
        wd.findElement(By.xpath("//input[@value='" + id + "']")).click();
        return null;
    }

    public void deleteSelected() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }


    public void submitModification() {
        click(By.name("update"));
    }

    public void goToHomePage() {
        click(By.linkText("home"));
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
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            ContactData contact = new ContactData()
                    .withId(id).withFirstname(firstname).withLastname(lastname)
                    .withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones);
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
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData()
                .withId(contact.getId()).withFirstname(firstname)
                .withLastname(lastname).withHomePhone(home)
                .withMobilePhone(mobile).withWorkPhone(work)
                .withAddress(address).withEmail(email)
                .withEmail2(email2).withEmail3(email3);

    }

    private void initModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

    public void removeFromGroupByName(ContactData contact, GroupData group) {
        selectGroupToRemoveByName(group);
        selectById(contact.getId());
        removeGroup();
        goToHomePage();
        selectAll();
    }

    public void selectAll() {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");

    }

    public void selectGroupToRemoveByName(GroupData group) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
    }


    public void removeGroup() {
        click(By.xpath("//input[@name='remove']"));
    }

    public void addToGroup(ContactData contact, GroupData group) {
        goToHomePage();
        selectById(contact.getId());
        selectGroupById(group.getId());
        addTo();
    }

    private void addTo() {
        wd.findElement(By.name("add")).click();
    }

    private void selectGroupById(int id) {
        wd.findElement(By.xpath("//select[@name='to_group']//option[@value='" + id + "']")).click();

    }

    public void selectThisGroup(int id) {
        wd.findElement(By.xpath("//select[@name='group']//option[@value='" + id + "']")).click();
    }

    public void selectNone() {
        wd.findElement(By.xpath("//option[@value='[none]']")).click();
    }
}
