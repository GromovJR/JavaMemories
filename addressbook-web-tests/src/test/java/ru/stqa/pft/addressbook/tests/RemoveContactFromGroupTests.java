package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.stqa.pft.addressbook.model.Contacts.getContactWithGroup;

public class RemoveContactFromGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        Groups groups = app.db().groups();
        if (groups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("TestName1").withHeader("TestHeader1").withFooter("TestFooter1"));
        }
        if (app.contact().all().size() == 0 ) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("NewName")
                    .withLastname("NewLastname")
                    .withAddress("NewAddress")
                    ,true);
        }
    }

    @Test(enabled = false)
    public void testContactRemove1() {
        app.goTo().homePage();
        ContactData before = getContactWithGroup(app.db().contacts());
        Groups groupsBefore = before.getGroups();
        GroupData groupToDelete = before.getGroups().iterator().next();
        app.contact().removeFromGroupByName(before, groupToDelete);
        ContactData after = app.db().contacts().getInfoOnContact(before);
        Groups groupsAfter = after.getGroups();

        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() - 1));
        assertThat(groupsAfter, equalTo(groupsBefore.without(groupToDelete)));

    }


    @Test(enabled = true)
    public void DeleteContactFromGroup(){
        app.goTo().homePage();
        ContactData contact = selectContact();
        app.goTo().startPage();
        GroupData groupForDeletion = selectedGroup(contact);
        Groups before = contact.getGroups();
        app.goTo().homePage();
        app.contact().removeFromGroupById(contact, groupForDeletion);
        ContactData contactsAfter = selectContactById(contact);
        Groups after = contactsAfter.getGroups();
        assertThat(after, equalTo(before.without(groupForDeletion)));
    }

    public ContactData selectContactById(ContactData contact) {
        Contacts contactById = app.db().contacts();
        return contactById.iterator().next().withId(contact.getId());
    }

    private GroupData selectedGroup(ContactData contactForDel) {
        ContactData con = selectContactById(contactForDel);
        Groups deletedContact = con.getGroups();
        return deletedContact.iterator().next();
    }

    private ContactData selectContact() {
        Contacts contacts = app.db().contacts();
        for (ContactData contact : contacts){
            if (contact.getGroups().size()>0){
                return contact;
            }
        }
        ContactData addContact = app.db().contacts().iterator().next();
        app.contact().addToGroup(addContact, app.db().groups().iterator().next());
        return addContact;
    }
}
