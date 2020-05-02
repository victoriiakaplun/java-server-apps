package ru.omsu.imit.daoimpl;

import org.apache.ibatis.session.SqlSession;
import ru.omsu.imit.mappers.AddressMapper;
import ru.omsu.imit.mappers.ContactMapper;
import ru.omsu.imit.mappers.ContactSectionMapper;
import ru.omsu.imit.mappers.SectionMapper;
import ru.omsu.imit.utils.MyBatisUtils;

public class DaoImplBase {
    protected SqlSession getSession() {
        MyBatisUtils.initSqlSessionFactory();
            return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected ContactMapper getContactMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ContactMapper.class);
    }

    protected SectionMapper getSectionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SectionMapper.class);
    }

    protected AddressMapper getAddressMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AddressMapper.class);
    }

    protected ContactSectionMapper getContactSectionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ContactSectionMapper.class);
    }

}
