package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase{

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().goToGroupPage();

        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("Test1", "Test1", "Test1"));
        app.getGroupHelper().submitGroupModification();
        app.getNavigationHelper().returnToGroupPage();

        int after = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(after, before);
    }
}
