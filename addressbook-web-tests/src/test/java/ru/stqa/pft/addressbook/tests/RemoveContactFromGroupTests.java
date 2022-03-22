package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.model.Contacts.getContactWithGroup;

public class RemoveContactFromGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        Groups groups = app.db().groups();
        if (groups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("TestName1").withHeader("TestHeader1").withFooter("TestFooter1"));
        }
        Groups updatedGroups = app.db().groups();

        if (app.contact().all().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("NewName")
                    .withLastname("NewLastname")
                    .withAddress("NewAddress")
                    .inGroup(updatedGroups.iterator().next()),
                    true);
        }
    }

    @Test
    public void testContactRemove() {
        app.goTo().homePage();
        ContactData before = getContactWithGroup(app.db().contacts());
        Groups groupsBefore = before.getGroups();
        GroupData groupToDelete = before.getGroups().iterator().next();
        app.contact().removeFromGroup(before, groupToDelete);
        ContactData after = app.db().contacts().getInfoOnContact(before);
        Groups groupsAfter = after.getGroups();

        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() - 1));
        assertThat(groupsAfter, equalTo(groupsBefore.without(groupToDelete)));

    }
}
