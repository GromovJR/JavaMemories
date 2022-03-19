package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() {
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

        app.getContactHelper().initContactModification(before.get(0));
        app.getContactHelper().fillContactForm(
                contact,
                false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());


        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }
}
