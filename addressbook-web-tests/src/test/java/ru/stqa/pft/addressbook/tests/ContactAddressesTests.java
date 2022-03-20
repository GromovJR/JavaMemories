package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressesTests extends TestBase {

    @Test(enabled = true)
    public void testContactAddress() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));

    }

    @Test(enabled = true)
    public void testContactEmails() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeAddress(ContactData contact) {
        return Stream.of(contact.getAddress()).filter((a) -> ! a.equals(""))
                .map(ContactAddressesTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private  String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((e) -> ! e.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String info) {
        return info.replaceAll("\\s", "").replaceAll("[-()']", "");
    }
}
