package ru.omsu.imit.daoimpl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.ContactSectionDao;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.model.Section;

import java.util.List;

public class ContactSectionDaoImpl extends DaoImplBase implements ContactSectionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactSectionDaoImpl.class);

    @Override
    public void addContactToSection(Contact contact, Section section) throws AddressBookException {
        LOGGER.debug("DAO add contact {} to section {}", contact, section);
        try(SqlSession sqlSession = getSession()) {
            try {
                getContactSectionMapper(sqlSession).addContactToSection(contact, section);
                contact.getSections().add(section);
                section.getSectionContacts().add(contact);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t add contact to section {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addContactToSections(Contact contact, List<Section> sections) throws AddressBookException {
        LOGGER.debug("DAO add contact to sections");
        try(SqlSession sqlSession = getSession()) {
            try {
                for(Section section: sections) {
                    getContactSectionMapper(sqlSession).addContactToSection(contact, section);
                    contact.getSections().add(section);
                    section.getSectionContacts().add(contact);
                }
            } catch (RuntimeException e) {
                LOGGER.info("Can`t add contact to sections {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void removeContactFromSection(Contact contact, Section section) throws AddressBookException {
        LOGGER.debug("DAO remove contact from section");
        try(SqlSession sqlSession = getSession()) {
            try {
                getContactSectionMapper(sqlSession).removeContactFromSection(contact, section);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t remove contact from section");
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<Section> getContactSections(Contact contact) throws AddressBookException {
        LOGGER.debug("DAO get contact sections");
        try(SqlSession sqlSession = getSession()) {
            try {
                return getContactSectionMapper(sqlSession).getContactSections(contact);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get contact sections {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<Contact> getSectionContacts(Section section) throws AddressBookException {
        LOGGER.debug("DAO get section contacts");
        try(SqlSession sqlSession = getSession()) {
            try {
                return getContactSectionMapper(sqlSession).getSectionContacts(section);
            } catch(RuntimeException e) {
                LOGGER.info("Can`t get section contacts {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
        }
    }
}
