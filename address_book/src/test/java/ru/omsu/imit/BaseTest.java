package ru.omsu.imit;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.omsu.imit.dao.*;
import ru.omsu.imit.daoimpl.*;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.utils.MyBatisUtils;

public class BaseTest {

    private CommonDao commonDao = new CommonDaoImpl();
    protected ContactDao contactDao = new ContactDaoImpl();
    protected AddressDao addressDao = new AddressDaoImpl();
    protected SectionDao sectionDao = new SectionDaoImpl();
    protected ContactSectionDao contactSectionDao = new ContactSectionDaoImpl();

    private static boolean setUpIsDone = false;

    @BeforeClass()
    public static void setUp() {
        if (!setUpIsDone) {
            Assume.assumeTrue(MyBatisUtils.initSqlSessionFactory());
            setUpIsDone = true;
        }
    }

    @Before()
    public void clearDatabase() throws AddressBookException {
        commonDao.clear();
    }

}
