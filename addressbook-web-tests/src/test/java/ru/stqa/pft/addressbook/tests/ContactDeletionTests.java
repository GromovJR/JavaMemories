package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.contact().list().size() == 0){
            app.contact().create(
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

        List<ContactData> before = app.contact().list();

        app.contact().select();
        app.contact().deleteSelected();
        app.goTo().goToHomePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
