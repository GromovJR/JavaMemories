package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() {
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(
                new ContactData(
                        "NewName",
                        "NewMiddlename",
                        "NewLastname",
                        "NewAddress",
                        "NewMobilePhone",
                        "NewEmail",
                        null),
                false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();

    }
}
