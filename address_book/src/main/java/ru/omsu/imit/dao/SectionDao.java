package ru.omsu.imit.dao;

import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Section;

import java.util.List;

public interface SectionDao {

    Section insert(Section section) throws AddressBookException;

    Section getById(int id) throws AddressBookException;

    List<Section> getAll() throws AddressBookException;

    void update(Section section) throws AddressBookException;

    void delete(int id) throws AddressBookException;

    void deleteAll() throws AddressBookException;
}

