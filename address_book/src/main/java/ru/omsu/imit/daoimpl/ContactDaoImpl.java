package ru.omsu.imit.daoimpl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.ContactDao;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

import java.util.List;

public class ContactDaoImpl extends DaoImplBase implements ContactDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactDaoImpl.class);

    @Override
    public Contact insert(Contact contact, Address address) throws AddressBookException {
        LOGGER.debug("DAO insert contact {} with address {}", contact, address);
        try (SqlSession sqlSession = getSession()) {
            try {
                getContactMapper(sqlSession).insert(contact);
                getAddressMapper(sqlSession).insert(address, contact);
                contact.setAddress(address);
                address.setContact(contact);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t add contact with address {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return contact;
    }

    @Override
    public Contact getById(int id) throws AddressBookException {
        LOGGER.debug("DAO get contact by id {}", id);
        try(SqlSession sqlSession = getSession()) {
            try {
                Contact contact = getContactMapper(sqlSession).getById(id);
                if(contact == null) {
                    throw new AddressBookException(ErrorCode.CONTACT_NOT_FOUND, Integer.toString(id));
                }
                return contact;
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get contact by id {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<Contact> getAll() throws AddressBookException {
        LOGGER.debug("DAO get all contacts");
        try(SqlSession sqlSession = getSession()) {
            try {
                return getContactMapper(sqlSession).getAll();
            } catch(RuntimeException e) {
                LOGGER.info("Can`t get all contacts {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public void update(Contact contact) throws AddressBookException {
        LOGGER.debug("DAO update contact {}", contact);
        try (SqlSession sqlSession = getSession()){
            try {
                getContactMapper(sqlSession). update(contact);
                getAddressMapper(sqlSession).update(contact.getAddress());
            } catch (RuntimeException e) {
                LOGGER.info("Can`t update contact {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(int id) throws AddressBookException {
        LOGGER.debug("DAO delete contact by id {}", id);
        try(SqlSession sqlSession = getSession()) {
            try {
                getContactMapper(sqlSession).delete(id);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete contact {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() throws AddressBookException {
        LOGGER.debug("DAO delete all contacts");
        try(SqlSession sqlSession = getSession()) {
            try {
                getContactMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete all contacts {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
