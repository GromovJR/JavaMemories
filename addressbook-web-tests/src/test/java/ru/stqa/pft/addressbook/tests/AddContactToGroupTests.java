package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class AddContactToGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        Groups groups = app.db().groups();
        if (groups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("TestName1").withHeader("TestHeader1").withFooter("TestFooter1"));
        }

        if (app.contact().all().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                            .withFirstname("NewName")
                            .withLastname("NewLastname")
                            .withAddress("NewAddress"),
                    true);
        }
    }

    @Test(enabled = true)
    public void testAddContactToGroup1() {
      ContactData contact = selectedContact();
      GroupData groupForAdd = selectedGroup(contact);
      Groups before = contact.getGroups();
      int beforeSize = contact.getGroups().size();


      app.goTo().homePage();
      app.contact().addToGroup(contact, groupForAdd);
      app.db().refresh(contact);
      Groups after = app.db().getContact(contact.getId()).getGroups();
      System.out.println("Групп у контакта до добавления -" + beforeSize + ". После добавления - " + contact.getGroups().size());
      assertThat(after, equalTo(before.withAdded(groupForAdd)));
      assertThat(beforeSize + 1, equalTo(contact.getGroups().size()));
    }


    public GroupData selectedGroup(ContactData contact) {
      Groups all = app.db().groups();
      Collection<GroupData> freeGroups = new HashSet<GroupData>(all);
      freeGroups.removeAll(contact.getGroups());
      return freeGroups.iterator().next();
    }

    public ContactData selectedContact() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < groups.size()) {
        return contact;
      }
    }
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("TestName1").withHeader("TestHeader1").withFooter("TestFooter1"));
    return contacts.iterator().next();
  }
}
