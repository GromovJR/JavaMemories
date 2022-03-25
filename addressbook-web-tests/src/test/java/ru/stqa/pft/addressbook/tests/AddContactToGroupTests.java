package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

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
    public void testAddContactToGroup() {
        ContactData contact = app.db().contacts().iterator().next();
        GroupData group = app.db().groups().iterator().next();

       if (contact.getGroups().contains(group)) {
            int beforeSize = contact.getGroups().size();
            app.contact().selectThisGroup(group.getId());
            GroupData groupToAddition = selectedGroup(contact);
            app.contact().addToGroup(contact, groupToAddition);
            app.db().refresh(contact);
            Assert.assertTrue(contact.getGroups().contains(groupToAddition));
            System.out.println("Групп у контакта до добавления -" + beforeSize + ". После добавления - " + contact.getGroups().size());
            assertThat(beforeSize + 1, equalTo(contact.getGroups().size()));

        } else  {
            int beforeSize = contact.getGroups().size();
            app.contact().selectThisGroup(contact.getGroups().iterator().next().getId());
            app.contact().addToGroup(contact, group);
            app.db().refresh(contact);
            Assert.assertTrue(contact.getGroups().contains(group));
            System.out.println("Групп у контакта до добавления -" + beforeSize + ". После добавления - " + contact.getGroups().size());
            assertThat(beforeSize + 1 , equalTo(contact.getGroups().size()));
        }
    }

    public GroupData selectedGroup(ContactData contact) {
        Groups all = app.db().groups();
        Collection<GroupData> freeGroups = new HashSet<GroupData>(all);
        if (freeGroups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("TestName1").withHeader("TestHeader1").withFooter("TestFooter1"));
            Collection<GroupData> actualFreeGroups = new HashSet<GroupData>(all);
            actualFreeGroups.removeAll(contact.getGroups());
            return actualFreeGroups.iterator().next();
        } else return freeGroups.iterator().next();

    }
}
