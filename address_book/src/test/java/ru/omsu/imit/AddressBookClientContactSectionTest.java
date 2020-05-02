package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.rest.dto.response.ContactWithAddressResponseDto;
import ru.omsu.imit.rest.dto.response.SectionResponseDto;

public class AddressBookClientContactSectionTest extends BaseClientTest {

    @Test
    public void testAddContactToSection() {
        ContactWithAddressResponseDto contactWithAddressResponse = addFirstnameContactWithAddress();
        SectionResponseDto sectionResponse = addTestSection();
        addContactToSection(1, contactWithAddressResponse.getId(), sectionResponse.getId(),
                sectionResponse.getTitle(), ErrorCode.SUCCESS);

    }

    @Test
    public void testAddContactToSections() {
        ContactWithAddressResponseDto contactWithAddressResponse = addFirstnameContactWithAddress();
        SectionResponseDto sectionResponse = addTestSection();
        addContactToSections(1, contactWithAddressResponse.getId(), sectionResponse.getId(),
                sectionResponse.getTitle(), ErrorCode.SUCCESS);
    }

    @Test
    public void testRemoveContactFromSection() {
        ContactWithAddressResponseDto contactWithAddressResponse = addFirstnameContactWithAddress();
        SectionResponseDto sectionResponse = addTestSection();
        removeContactFromSection(0, contactWithAddressResponse.getId(), sectionResponse.getId(), ErrorCode.SUCCESS);
    }

    private ContactWithAddressResponseDto addFirstnameContactWithAddress(){
        return addContactWithAddress("firstname", "lastname", "patronymic", "2020-01-01",
                "1234567890", "email@gmail.com", "123456", "country",
                "city", "street", "house", "block", "flat", ErrorCode.SUCCESS);
    }

    private SectionResponseDto addTestSection(){
        return addSection("test", ErrorCode.SUCCESS);
    }
}
