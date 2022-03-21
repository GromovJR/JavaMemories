package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0){
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("Firstname").withLastname("Lastname")
                    .withAddress("Address").withMobilePhone("MobilePhone")
                    .withEmail("Email"),
                    true);
        }
    }

    @Test(enabled = true )
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.stream().iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstname("NewFirstname").withLastname("NewLastname")
                .withAddress("NewAddress").withMobilePhone("NewMobilePhone")
                .withEmail("NewEmail");
        app.goTo().homePage();
        app.contact().modify(contact,false);

        Assert.assertEquals(app.contact().getCount(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
