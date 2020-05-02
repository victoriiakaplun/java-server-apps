package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ContactTest extends BaseTest {

    private Contact createContactIvanovAndInsert() throws AddressBookException {
        Contact contactToBeInserted = new Contact("Ivan", "Ivanov", "Ivanovic", LocalDate.now(),
                "123456", "ivan@mail.ru");
        Address addressToBeInserted = new Address(contactToBeInserted, "1111", "Russia", "Omsk", "Kosmos",
                "11", "-", "21");
        contactDao.insert(contactToBeInserted, addressToBeInserted);
        return contactToBeInserted;
    }

    private Contact createContactPetrovAndInsert() throws AddressBookException {
        Contact contactToBeInserted1 = new Contact("Petr", "Petrov", "Petrovic", LocalDate.now(),
                "1234567", "petr@mail.ru");
        Address addressToBeInserted1 = new Address(contactToBeInserted1, "2222", "Russia", "Omsk", "Mira",
                "55", "a", "214");
        contactDao.insert(contactToBeInserted1, addressToBeInserted1);
        return contactToBeInserted1;
    }

    @Test
    public void testInsertContactWithAddress() throws AddressBookException {
        Contact contact = createContactIvanovAndInsert();
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), contactFromDB.getAddress());
    }

    @Test(expected = AddressBookException.class)
    public void testInsertContactWithNullParam() throws AddressBookException {
        Contact contactToBeInserted = new Contact(null, "Ivanov", "Ivanovic", LocalDate.now(),
                "123456", "ivan@mail.ru");
        Address addressToBeInserted = new Address(contactToBeInserted,"1111", "Russia", "Omsk", "Kosmos",
                "11", "-", "21");
        contactDao.insert(contactToBeInserted, addressToBeInserted);
    }

    @Test(expected = AddressBookException.class)
    public void testGetNonexistentContactById() throws AddressBookException {
        assertNull(contactDao.getById(0));
    }

    @Test
    public void testUpdateContact() throws AddressBookException {
        Contact contact = createContactIvanovAndInsert();
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), contactFromDB.getAddress());
        contact.setLastName("Petrov");
        contactDao.update(contact);
        Contact updatedContactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, updatedContactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), updatedContactFromDB.getAddress());
    }

    @Test(expected = AddressBookException.class)
    public void testUpdateContactSetNullParam() throws AddressBookException {
        Contact contact = createContactIvanovAndInsert();
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), contactFromDB.getAddress());
        contact.setLastName(null);
        contactDao.update(contact);
    }

    @Test
    public void testDeleteContact() throws AddressBookException {
        Contact contact = createContactIvanovAndInsert();
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), contactFromDB.getAddress());
        contactDao.delete(contact.getId());
        assertEquals(0, contactDao.getAll().size());
    }

    @Test
    public void testInsertAndDeleteTwoContact() throws AddressBookException {
        Contact contact = createContactIvanovAndInsert();
        Contact contact1 = createContactPetrovAndInsert();
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        contacts.add(contact1);
        List<Contact> contactsFromDB = contactDao.getAll();
        assertEquals(2, contactsFromDB.size());
        contactDao.deleteAll();
        contactsFromDB = contactDao.getAll();
        assertEquals(0, contactsFromDB.size());
    }

    @Test
    public void testGetContactsFromEmptyTable() throws AddressBookException {
        assertEquals(0, contactDao.getAll().size());
    }


}
