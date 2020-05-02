package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

import java.time.LocalDate;

public class AddressTest extends BaseTest {

    @Test
    public void testUpdateAddress() throws AddressBookException {
        Contact contact = createContactIvanovAndInsert();
        Contact contactFromDB = contactDao.getById(contact.getId());
        Address contactAddress = contact.getAddress();
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contactAddress, contactFromDB.getAddress());
        contactAddress.setFlat("22");
        addressDao.update(contactAddress);
        Contact updatedContactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), updatedContactFromDB.getAddress());
    }

    private Contact createContactIvanovAndInsert() throws AddressBookException {
        Contact contactToBeInserted = new Contact("Ivan", "Ivanov", "Ivanovic", LocalDate.now(),
                "123456", "ivan@mail.ru");
        Address addressToBeInserted = new Address(contactToBeInserted, "1111", "Russia", "Omsk", "Kosmos",
                "11", "-", "21");
        contactDao.insert(contactToBeInserted, addressToBeInserted);
        return contactToBeInserted;
    }
}

