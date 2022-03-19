package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void selectGroup() {
        if (! isElementPresent(By.name("selected[]"))) {
            goToGroupPage();
            initGroupCreation();
            fillGroupForm(new GroupData("Test1", null, null));
            submitGroupCreation();
            goToGroupPage();
        }
        click(By.name("selected[]"));
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    private void goToGroupPage() {
        click(By.linkText("groups"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
}
