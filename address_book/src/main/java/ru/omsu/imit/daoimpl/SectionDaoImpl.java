package ru.omsu.imit.daoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.SqlSession;
import ru.omsu.imit.dao.SectionDao;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.model.Section;

import java.util.List;

public class SectionDaoImpl extends DaoImplBase implements SectionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SectionDaoImpl.class);

    @Override
    public Section insert(Section section) throws AddressBookException {
        LOGGER.debug("DAO insert section {}", section);
        try(SqlSession sqlSession = getSession()) {
            try{
                getSectionMapper(sqlSession).insert(section);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t add section {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return section;
    }

    @Override
    public Section getById(int id) throws AddressBookException {
        LOGGER.debug("DAO get section by id {}", id);
        try(SqlSession sqlSession = getSession()) {
            try {
                Section section =  getSectionMapper(sqlSession).getById(id);
                if(section == null) {
                    throw new AddressBookException(ErrorCode.SECTION_NOT_FOUND, Integer.toString(id));
                }
                return section;
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get section by id {}", e);
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<Section> getAll() throws AddressBookException {
        LOGGER.debug("DAO get all sections");
        try(SqlSession sqlSession = getSession()) {
            try {
                return getSectionMapper(sqlSession).getAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get all sections {}", e);
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public void update(Section section) throws AddressBookException {
        LOGGER.debug("DAO update section {}", section);
        try(SqlSession sqlSession = getSession()) {
            try {
                getSectionMapper(sqlSession).update(section);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t update section {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(int id) throws AddressBookException {
        LOGGER.debug("DAO delete section by id {}", id);
        try(SqlSession sqlSession = getSession()) {
            try {
                getSectionMapper(sqlSession).delete(id);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete section {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() throws AddressBookException {
        LOGGER.debug("DAO delete all sections");
        try(SqlSession sqlSession = getSession()) {
            try {
                getSectionMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete all sections {}", e);
                sqlSession.rollback();
                throw new AddressBookException(ErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
