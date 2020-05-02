package ru.omsu.imit.dao;

import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;


public interface AddressDao {

    void insert(Address address, Contact contact) throws AddressBookException;

    void update(Address address) throws AddressBookException;

}
