package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {
        ContactData contact = new ContactData(
                "NewName",
                "NewLastname",
                "NewAddress",
                "NewMobilePhone",
                "NewEmail",
                null);

        if (! app.getContactHelper().tryToSearchContact()){
            app.getContactHelper().createContact(
                    contact,
                    true);
        }

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getNavigationHelper().goToHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
