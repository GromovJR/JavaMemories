package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {


    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitCreation() {
        click(By.name("submit"));
    }

    public void fillForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initCreation() {
        click(By.name("new"));
    }

    public void deleteSelected() {
        click(By.name("delete"));
    }

    public void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id +"']")).click();
    }

    public void initModification() {
        click(By.name("edit"));
    }

    public void goToGroupPage() {
        click(By.linkText("groups"));
    }

    public void submitModification() {
        click(By.name("update"));
    }

    public void create(GroupData groupData) {
        initCreation();
        fillForm(groupData);
        submitCreation();
        goToGroupPage();
    }

    public void modify(GroupData groupData) {
        selectById(groupData.getId());
        initModification();
        fillForm(groupData);
        submitModification();
        goToGroupPage();

    }

    public void delete(GroupData group) {
        selectById(group.getId());
        deleteSelected();
        goToGroupPage();
    }

    public boolean tryToSearch() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public Groups all() {
        Groups groups = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groups.add(new GroupData().withId(id).withName(name));
        }
        return groups;
    }

}
