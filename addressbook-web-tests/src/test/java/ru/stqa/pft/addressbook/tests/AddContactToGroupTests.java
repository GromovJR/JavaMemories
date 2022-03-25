package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTests extends TestBase{

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

    @Test(enabled = true)
    public void testAddContactToGroup1() {
        Contacts contactBefore = app.db().contacts();
        ContactData contact = app.db().contacts().iterator().next();
        GroupData group = app.db().groups().iterator().next();

        if (contact.getGroups().contains(group)) {
            app.contact().selectThisGroup(group.getId());
            GroupData groupToAddition = app.db().groups().without(group).iterator().next();
            app.contact().addToGroup(contact, groupToAddition);
            app.db().refresh(contact);
            Assert.assertTrue(contact.getGroups().contains(groupToAddition));
        } else  {
            app.contact().selectNone();
            app.contact().addToGroup(contact, group);
            app.db().refresh(contact);
            Assert.assertTrue(contact.getGroups().contains(group));
        }

        Contacts contactAfter = app.db().contacts();
        assertThat(contactAfter.size(), equalTo(contactBefore.size()));
    }
}
