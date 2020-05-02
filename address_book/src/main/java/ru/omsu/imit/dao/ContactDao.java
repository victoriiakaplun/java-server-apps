package ru.omsu.imit.dao;

import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

import java.util.List;

public interface ContactDao {

    Contact insert(Contact contact, Address address) throws AddressBookException;

    Contact getById(int id) throws AddressBookException;

    List<Contact> getAll() throws AddressBookException;

    void update(Contact contact) throws AddressBookException;

    void delete(int id) throws AddressBookException;

    void deleteAll() throws AddressBookException;
}
