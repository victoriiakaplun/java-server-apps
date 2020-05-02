package ru.omsu.imit.database.jdbc;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.omsu.imit.database.model.Book;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcServiceTest {

    private static boolean setUpIsDone = false;
    private static Connection connection;

    @BeforeClass()
    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;

        }
    }

    @AfterClass
    public static void close() {
        if (setUpIsDone)
            JdbcUtils.closeConnection();
    }

    @Before()
    public void clearDatabase() throws SQLException {
        JdbcService.deleteAllBooks(JdbcUtils.getConnection());
        connection = JdbcUtils.getConnection();
    }

    @Test
    public void testInsertBook() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        JdbcService.insertBook(connection, book);
        Book bookFromDB = JdbcService.getBookById(connection, book.getId());
        assertEquals(book, bookFromDB);
    }

    @Test
    public void testGetAllBooks() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Good book", 1999, 2600, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
    }

    @Test
    public void testGetBooksUseLimit() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Good book", 1999, 2600, null, "no");
        Book book2 = new Book(0, "Great book", 2000, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksUseLimit(connection,2 );
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
     }

    @Test
    public void testGetBooksWithGivenTitle() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 2600, null, "no");
        Book book2 = new Book(0, "Great book", 2000, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksWithGivenTitle(connection, "Awesome book");
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
        List<Book> noBooksFromDB = JdbcService.getBooksWithGivenTitle(connection, "Other book");
        assertEquals(0, noBooksFromDB.size());
    }

    @Test
    public void testGetBooksWithStartsTitleSubstring() throws SQLException{
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 2600, null, "no");
        Book book2 = new Book(0, "Great book", 2000, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksWithStartsTitleSubstring(connection, "Awesome");
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
        List<Book> noBooksFromDB = JdbcService.getBooksWithStartsTitleSubstring(connection, "Normal");
        assertEquals(0, noBooksFromDB.size());
    }

    @Test
    public void testGetBooksWithEndsTitleSubstring() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 2600, null, "no");
        Book book2 = new Book(0, "Great boook", 2000, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksWithEndsTitleSubstring(connection, "book");
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
        List<Book> noBooksFromDB = JdbcService.getBooksWithEndsTitleSubstring(connection, "aaa");
        assertEquals(0, noBooksFromDB.size());
    }

    @Test
    public void testGetBooksWithYearsInfo() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 2600, null, "no");
        Book book2 = new Book(0, "Great boook", 2004, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksWithYearsInfo(connection, 1900,2000);
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
        List<Book> noBooksFromDB = JdbcService.getBooksWithYearsInfo(connection, 2010, 2019);
        assertEquals(0, noBooksFromDB.size());
    }

    @Test
    public void testGetBooksWithPagesInfo() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 2600, null, "no");
        Book book2 = new Book(0, "Great boook", 2004, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksWithPagesInfo(connection, 200,300);
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book2));
        List<Book> noBooksFromDB = JdbcService.getBooksWithPagesInfo(connection, 2010, 2019);
        assertEquals(0, noBooksFromDB.size());
    }

    @Test
    public void testGetBooksWithPagesAndYearInfo() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 260, null, "no");
        Book book2 = new Book(0, "Great boook", 2004, 2600, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        List<Book> booksFromDB = JdbcService.getBooksWithPagesAndYearInfo(connection,
                1900,2000, 200, 300);
        assertEquals(2, booksFromDB.size());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
        List<Book> noBooksFromDB = JdbcService.getBooksWithPagesAndYearInfo(connection,
                2010, 2019,260, 2000 );
        assertEquals(0, noBooksFromDB.size());
    }

    @Test
    public void testUpdateBooksPages() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.updateBooksPages(connection, 1000);
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        for(Book b: booksFromDB) {
            assertEquals(1000, b.getPages());
        }
    }

    @Test
    public void testUpdateBooksByPageLowerBorder() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 260, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.updateBooksByPageLowerBorder(connection, 250, 1000);
        Book bookFromDB = JdbcService.getBookById(connection, book.getId());
        assertEquals(200, bookFromDB.getPages());
        Book bookFromDB1 = JdbcService.getBookById(connection, book1.getId());
        assertEquals(1000, bookFromDB1.getPages());
    }

    @Test
    public void testUpdateBooksByPageAndYearInfo() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.updateBooksByPageAndYearInfo(connection, 1000, 190, 1995);
        Book bookFromDB = JdbcService.getBookById(connection, book.getId());
        assertEquals(1000, bookFromDB.getPages());
        Book bookFromDB1 = JdbcService.getBookById(connection, book1.getId());
        assertEquals(160, bookFromDB1.getPages());
    }

    @Test
    public void testUpdateBooksByPagesAndTitleInfo() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.updateBooksByPagesAndTitleInfo(connection, 1000, 170, "Awesome book");
        Book bookFromDB = JdbcService.getBookById(connection, book.getId());
        assertEquals(1000, bookFromDB.getPages());
        Book bookFromDB1 = JdbcService.getBookById(connection, book1.getId());
        assertEquals(160, bookFromDB1.getPages());
    }

    @Test
    public void testDeleteBooksByPageLowerBorder() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.deleteBooksByPageLowerBorder(connection, 170);
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        assertFalse(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
    }

    @Test
    public void testDeleteBooksWithTitleInfo() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.deleteBooksWithTitleInfo(connection, "Awesome");
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        assertFalse(booksFromDB.contains(book));
        assertTrue(booksFromDB.contains(book1));
    }

    @Test
    public void testDeleteBooksWithIds() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        Book book2 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        Book book3 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        JdbcService.insertBook(connection, book3);
        JdbcService.deleteBooksWithIds(connection, book.getId(), book2.getId());
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        assertFalse(booksFromDB.contains(book));
        assertFalse(booksFromDB.contains(book1));
        assertFalse(booksFromDB.contains(book2));
        assertTrue(booksFromDB.contains(book3));
        assertEquals(1, booksFromDB.size());
    }

    @Test
    public void testDeleteBooksByTitle() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.deleteBooksByTitle(connection, "Not awesome book");
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        assertTrue(booksFromDB.contains(book));
        assertFalse(booksFromDB.contains(book1));
        assertEquals(1, booksFromDB.size());
    }

    @Test
    public void testDeleteBooksWithTitles() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "So awesome book", 1999, 160, null, "no");
        Book book2 = new Book(0, "Very awesome book", 1999, 160, null, "no");
        Book book3 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        JdbcService.insertBook(connection, book3);
        JdbcService.deleteBooksWithTitles(connection,
                "Awesome book",
                "So awesome book",
                "Very awesome book"
        );
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        assertFalse(booksFromDB.contains(book));
        assertFalse(booksFromDB.contains(book1));
        assertFalse(booksFromDB.contains(book2));
        assertTrue(booksFromDB.contains(book3));
        assertEquals(1, booksFromDB.size());
    }

    @Test
    public void testDeleteAllBooks() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.deleteAllBooks(connection);
        assertEquals(0, JdbcService.getAllBooks(connection).size());
    }

    @Test
    public void testUpdateBooksPublishingHouse() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "no");
        Book book1 = new Book(0, "So awesome book", 1999, 160, null, "no");
        Book book2 = new Book(0, "Very awesome book", 1999, 160, null, "no");
        Book book3 = new Book(0, "Not awesome book", 1999, 160, null, "no");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.insertBook(connection, book2);
        JdbcService.insertBook(connection, book3);
        JdbcService.updateBooksPublishingHouse(connection, book.getId(),book2.getId(), "publishing house");
        assertEquals("publishing house", JdbcService.getBookById(connection, book.getId()).getPublishingHouse());
        assertEquals("publishing house", JdbcService.getBookById(connection, book1.getId()).getPublishingHouse());
        assertEquals("publishing house", JdbcService.getBookById(connection, book2.getId()).getPublishingHouse());
        assertNull(JdbcService.getBookById(connection, book3.getId()).getPublishingHouse());
    }

    @Test
    public void testSetNoBindingType() throws SQLException {
        Book book = new Book(0, "Awesome book", 1990, 200, null, "soft");
        Book book1 = new Book(0, "Not awesome book", 1999, 160, null, "hard");
        JdbcService.insertBook(connection, book);
        JdbcService.insertBook(connection, book1);
        JdbcService.setNoBindingType(connection);
        List<Book> booksFromDB = JdbcService.getAllBooks(connection);
        booksFromDB.forEach(b -> assertEquals("no", b.getBinding()));
    }

}
