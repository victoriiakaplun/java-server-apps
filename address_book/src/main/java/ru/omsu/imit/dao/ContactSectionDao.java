package ru.omsu.imit.dao;

import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.model.Section;

import java.util.List;

public interface ContactSectionDao {

    void addContactToSection(Contact contact, Section section) throws AddressBookException;

    void addContactToSections(Contact contact, List<Section> sections) throws AddressBookException;

    void removeContactFromSection(Contact contact, Section section) throws AddressBookException;

    List<Section> getContactSections(Contact contact) throws AddressBookException;

    List<Contact> getSectionContacts(Section section) throws AddressBookException;
}
