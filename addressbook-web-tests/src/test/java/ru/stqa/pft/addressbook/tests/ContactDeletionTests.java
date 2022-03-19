package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (! app.getContactHelper().tryToSearchContact()){
            app.getContactHelper().createContact(
                    new ContactData(
                            "NewName",
                            "NewLastname",
                            "NewAddress",
                            "NewMobilePhone",
                            "NewEmail",
                            null),
                    true);
        }
    }

    @Test(enabled = false)
    public void testContactDeletion() {
        ContactData contact = new ContactData(
                "NewName",
                "NewLastname",
                "NewAddress",
                "NewMobilePhone",
                "NewEmail",
                null);

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
