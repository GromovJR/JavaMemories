package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    //File photo = new File("src/test/resources/logo.png");
    ContactData contact = new ContactData()
            .withFirstname("Firstname").withLastname("Lastname")
            .withAddress("Address").withMobilePhone("MobilePhone")
            .withEmail("Email").withGroup("Test1");
    app.contact().create(contact, true);

    Assert.assertEquals(app.contact().getCount(), before.size() + 1);
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
