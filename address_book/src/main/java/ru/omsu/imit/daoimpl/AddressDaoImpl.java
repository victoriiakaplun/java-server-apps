package ru.omsu.imit.daoimpl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.AddressDao;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

public class AddressDaoImpl extends DaoImplBase implements AddressDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDaoImpl.class);

    @Override
    public void insert(Address address, Contact contact) throws AddressBookException {
        LOGGER.debug("DAO insert address {}", address);
        try(SqlSession sqlSession  = getSession()) {
            try {
                getAddressMapper(sqlSession).insert(address, contact);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t insert address {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(Address address) throws AddressBookException {
        LOGGER.debug("DAO update address {}", address);
        try(SqlSession sqlSession = getSession()) {
            try {
                getAddressMapper(sqlSession).update(address);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t update address {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
