package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Section;

import java.util.List;

import static org.junit.Assert.*;

public class SectionTest extends BaseTest {

    private Section createSectionFriendsAndInsert() throws AddressBookException {
        Section sectionToBeInserted = new Section("friends");
        sectionDao.insert(sectionToBeInserted);
        return sectionToBeInserted;
    }

    private Section createSectionFamilyAndInsert() throws AddressBookException {
        Section sectionToBeInserted = new Section("friends");
        sectionDao.insert(sectionToBeInserted);
        return sectionToBeInserted;
    }

    @Test
    public void testInsertSection() throws AddressBookException {
        Section section = createSectionFriendsAndInsert();
        Section sectionFromDB = sectionDao.getById(section.getId());
        FieldsAssertionsUtils.checkSectionFields(section, sectionFromDB);
    }

    @Test(expected = AddressBookException.class)
    public void testInsertSectionFailed() throws AddressBookException {
        sectionDao.insert(new Section(null));
    }

    @Test(expected = AddressBookException.class)
    public void testGetSectionWithNonexistentId() throws AddressBookException {
        sectionDao.getById(0);
    }

    @Test
    public void testGetAllSectionFromEmptyTable() throws AddressBookException {
        assertEquals(0, sectionDao.getAll().size());
    }

    @Test
    public void testGetAllSection() throws AddressBookException {
        Section section = createSectionFriendsAndInsert();
        Section section1 = createSectionFamilyAndInsert();
        List<Section> sections = sectionDao.getAll();
        assertEquals(2, sections.size());
        assertTrue(sections.contains(section));
        assertTrue(sections.contains(section1));
    }

    @Test
    public void testUpdateSection() throws AddressBookException {
        Section section = createSectionFriendsAndInsert();
        Section sectionFromDB = sectionDao.getById(section.getId());
        FieldsAssertionsUtils.checkSectionFields(section, sectionFromDB);
        section.setTitle("family");
        sectionDao.update(section);
        Section updatedSectionFromDB = sectionDao.getById(section.getId());
        FieldsAssertionsUtils.checkSectionFields(section, updatedSectionFromDB);
    }

    @Test
    public void testInsertDeleteSection() throws AddressBookException {
        Section section = createSectionFriendsAndInsert();
        Section sectionFromDB = sectionDao.getById(section.getId());
        FieldsAssertionsUtils.checkSectionFields(section, sectionFromDB);
        sectionDao.delete(section.getId());
        assertEquals(0, sectionDao.getAll().size());
    }

    @Test
    public void testInsertAndDeleteSections() throws AddressBookException {
        Section section = createSectionFriendsAndInsert();
        Section section1 = createSectionFamilyAndInsert();
        List<Section> sections = sectionDao.getAll();
        assertEquals(2, sections.size());
        assertTrue(sections.contains(section));
        assertTrue(sections.contains(section1));
        sectionDao.deleteAll();
        sections = sectionDao.getAll();
        assertEquals(0, sections.size());
    }
}
