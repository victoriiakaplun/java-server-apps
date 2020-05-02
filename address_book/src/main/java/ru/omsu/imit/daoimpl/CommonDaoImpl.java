package ru.omsu.imit.daoimpl;


import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.CommonDao;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;

public class CommonDaoImpl extends DaoImplBase implements CommonDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);

    @Override
    public void clear() throws AddressBookException {
        LOGGER.debug("DAO clear database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getContactMapper(sqlSession).deleteAll();
                getSectionMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t clear database {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
