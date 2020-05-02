package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.rest.dto.response.ContactWithAddressResponseDto;

import java.time.LocalDate;

public class AddressBookClientContactTest extends BaseClientTest{

    @Test
    public void testAddContactWithAddress() {
        addFirstnameContactWithAddress();
    }

    @Test
    public void testGetContactById() {
        ContactWithAddressResponseDto contactWithAddressResponse = addFirstnameContactWithAddress();
        getFirstnameContactById(contactWithAddressResponse.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testGetContactByWrongId() {
        getFirstnameContactById(1000, ErrorCode.CONTACT_NOT_FOUND);
    }

    @Test
    public void testGetAllContacts() {
      addFirstnameContactWithAddress();
      addTestContactWithAddress();
        getAllContacts(2, ErrorCode.SUCCESS);
    }

    @Test
    public void testGetAllContactsWhenEmpty() {
        getAllContacts(0, ErrorCode.SUCCESS);
    }

    @Test
    public void testEditContactById() {
        ContactWithAddressResponseDto contactWithAddressResponse = addFirstnameContactWithAddress();
        editFirstnameContactById(contactWithAddressResponse.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testEditContactByWrongId() {
        editFirstnameContactById(10000, ErrorCode.CONTACT_NOT_FOUND);
    }

    @Test
    public void testDeleteContactById() {
        ContactWithAddressResponseDto contactWithAddressResponse = addFirstnameContactWithAddress();
        deleteContactById(contactWithAddressResponse.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testDeleteAllContacts() {
        addFirstnameContactWithAddress();
        addTestContactWithAddress();
        deleteAllContacts(ErrorCode.SUCCESS);
    }

    private ContactWithAddressResponseDto addFirstnameContactWithAddress(){
        return addContactWithAddress("firstname", "lastname", "patronymic", "2020-01-01",
                "1234567890", "email@gmail.com", "123456", "country",
                "city", "street", "house", "block", "flat", ErrorCode.SUCCESS);
    }

    private ContactWithAddressResponseDto addTestContactWithAddress(){
        return addContactWithAddress("test", "test", "test", "2020-01-01",
                "12345678900", "test@gmail.com", "1234567", "country",
                "city", "street", "house", "block", "flat", ErrorCode.SUCCESS);
    }

    private void getFirstnameContactById(int id, ErrorCode errorCode) {
        getContactById(id, "firstname", "lastname", "patronymic",
                "2020-01-01", "1234567890", "email@gmail.com", "123456", "country",
                "city", "street", "house", "block", "flat", errorCode);
    }

    private void editFirstnameContactById(int id, ErrorCode errorCode) {
        editContactById(id,"newfirstname",
                "newlastname", "newpatronymic", "2020-01-01", "001234567890",
                "newemail@gmail.com", "123456", "newcountry",
                "newcity", "newstreet", "newhouse", "newblock", "newflat", errorCode);
    }
}
