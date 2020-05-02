package ru.omsu.imit;

import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.model.Section;

import static org.junit.Assert.assertEquals;

public class FieldsAssertionsUtils {
    protected  static void checkAddressFields(Address address, Address address1) {
        assertEquals(address.getPostcode(), address1.getPostcode());
        assertEquals(address.getCountry(), address1.getCountry());
        assertEquals(address.getCity(), address1.getCity());
        assertEquals(address.getStreet(), address1.getStreet());
        assertEquals(address.getHouse(), address1.getHouse());
        assertEquals(address.getBlock(), address1.getBlock());
        assertEquals(address.getFlat(), address.getFlat());
    }

    protected static void checkContactFields(Contact contact, Contact contact1) {
        assertEquals(contact.getId(), contact1.getId());
        assertEquals(contact.getFirstName(), contact1.getFirstName());
        assertEquals(contact.getLastName(), contact1.getLastName());
        assertEquals(contact.getBirthdate(), contact1.getBirthdate());
        assertEquals(contact.getPatronymic(), contact1.getPatronymic());
        assertEquals(contact.getEmail(), contact1.getEmail());
        assertEquals(contact.getPhone(), contact1.getPhone());
    }

    protected static void checkSectionFields(Section section, Section section1) {
        assertEquals(section.getId(), section1.getId());
        assertEquals(section.getTitle(), section1.getTitle());
    }
}
