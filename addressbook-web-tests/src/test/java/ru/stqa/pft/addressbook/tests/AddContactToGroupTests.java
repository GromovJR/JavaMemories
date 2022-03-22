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

    @Test
    public void testAddContactToGroup() {
        ContactData contact = app.db().contacts().iterator().next();
        GroupData group = app.db().groups().iterator().next();

        if (contact.getGroups().equals(group)) {
            app.goTo().homePage();
            app.contact().removeFromGroup(contact, group);
        }

        app.db().refresh(contact);
        Contacts before = app.db().contacts();
        app.contact().addToGroup(contact, group);
        app.db().refresh(contact);

        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(contact.getGroups(), hasItem(group));

    }
}
