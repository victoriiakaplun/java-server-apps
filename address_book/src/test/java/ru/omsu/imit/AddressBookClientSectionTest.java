package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.rest.dto.response.SectionResponseDto;

public class AddressBookClientSectionTest extends BaseClientTest {

    @Test
    public void testAddSection() {
        addSection("friends", ErrorCode.SUCCESS);
    }

    @Test
    public void testGetAllSections() {
        addSection("friends", ErrorCode.SUCCESS);
        addSection("family", ErrorCode.SUCCESS);
        addSection("other", ErrorCode.SUCCESS);
        getAllSections(3, ErrorCode.SUCCESS);
    }

    @Test
    public void testGetAllSectionsWhenEmpty() {
        getAllSections(0, ErrorCode.SUCCESS);
    }

    @Test
    public void testEditSectionById() {
        SectionResponseDto addResponse = addSection("title", ErrorCode.SUCCESS);
        editSectionById(addResponse.getId(), "titletitle", ErrorCode.SUCCESS);
    }

    @Test
    public void testEditSectionByWrongId() {
        editSectionById(1000, "titletitle", ErrorCode.SECTION_NOT_FOUND);
    }

    @Test
    public void testDeleteSectionById() {
        SectionResponseDto addResponse = addSection("title", ErrorCode.SUCCESS);
        deleteSectionById(addResponse.getId(), ErrorCode.SUCCESS);
    }
}
