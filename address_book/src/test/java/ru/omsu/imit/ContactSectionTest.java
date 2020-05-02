package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.model.Section;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactSectionTest extends BaseTest {

    @Test
    public void testAddContactToSection() throws AddressBookException {
        Contact contactToBeInserted = new Contact("Ivan", "Ivanov", "Ivanovic", LocalDate.now(),
                "123456", "ivan@mail.ru");
        Address addressToBeInserted = new Address(contactToBeInserted,"1111", "Russia", "Omsk", "Kosmos",
                "11", "-", "21");
        Contact contact = contactDao.insert(contactToBeInserted, addressToBeInserted);
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), contactFromDB.getAddress());
        Section section = sectionDao.insert(new Section("friends"));
        Section sectionFromDB = sectionDao.getById(section.getId());
        FieldsAssertionsUtils.checkSectionFields(section, sectionFromDB);
        List<Section> contactSections = new ArrayList<>();
        contactSections.add(section);
        contactSectionDao.addContactToSection(contact, section);
        Contact contactFromDBWithSections = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkSectionFields(contact.getSections().get(0), contactFromDBWithSections.getSections().get(0));
    }

    @Test
    public void testAddContactToSections() throws AddressBookException {
        Contact contactToBeInserted = new Contact("Ivan", "Ivanov", "Ivanovic", LocalDate.now(),
                "123456", "ivan@mail.ru");
        Address addressToBeInserted = new Address(contactToBeInserted,"1111", "Russia", "Omsk", "Kosmos",
                "11", "-", "21");
        Contact contact = contactDao.insert(contactToBeInserted, addressToBeInserted);
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        FieldsAssertionsUtils.checkAddressFields(contact.getAddress(), contactFromDB.getAddress());
        Section section = sectionDao.insert(new Section("friends"));
        Section section1 = sectionDao.insert(new Section("family"));
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        sections.add(section1);
        List<Section> sectionsFromDB = sectionDao.getAll();
        assertTrue(sectionsFromDB.contains(section));
        assertTrue(sectionsFromDB.contains(section1));
        contactSectionDao.addContactToSections(contact, sections);
        Contact contactWithSectionsFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkSectionFields(section, contactWithSectionsFromDB.getSections().get(0));
        FieldsAssertionsUtils.checkSectionFields(section1, contactWithSectionsFromDB.getSections().get(1));
    }

    @Test
    public void testRemoveContactFromSection() throws AddressBookException {
        Contact contactToBeInserted = new Contact("Ivan", "Ivanov", "Ivanovic", LocalDate.now(),
                "123456", "ivan@mail.ru");
        Address addressToBeInserted = new Address(contactToBeInserted, "1111", "Russia", "Omsk", "Kosmos",
                "11", "-", "21");
        Contact contact = contactDao.insert(contactToBeInserted, addressToBeInserted);
        Contact contactFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkContactFields(contact, contactFromDB);
        Section section = sectionDao.insert(new Section("friends"));
        Section section1 = sectionDao.insert(new Section("family"));
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        sections.add(section1);
        List<Section> sectionsFromDB = sectionDao.getAll();
        assertEquals(sections, sectionsFromDB);
        contactSectionDao.addContactToSections(contact, sections);
        Contact contactWithSectionsFromDB = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkSectionFields(section, contactWithSectionsFromDB.getSections().get(0));
        FieldsAssertionsUtils.checkSectionFields(section1, contactWithSectionsFromDB.getSections().get(1));
        contactSectionDao.removeContactFromSection(contact, section1);
        sections.remove(section1);
        Contact contactWithSectionsFromDB1 = contactDao.getById(contact.getId());
        FieldsAssertionsUtils.checkSectionFields(section, contactWithSectionsFromDB1.getSections().get(0));
        assertEquals(1, contactWithSectionsFromDB1.getSections().size());
    }

}
