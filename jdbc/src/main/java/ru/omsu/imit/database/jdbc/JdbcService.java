package ru.omsu.imit.database.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.database.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcService.class);

    public static void insertBook(Connection connection, Book book)  throws SQLException {
        LOGGER.debug("JdbcService insert book");
        String insertQuery = "INSERT INTO books(title, year, pages) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getYear());
            preparedStatement.setInt(3, book.getPages());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt((1)));
                }
            }

        } catch (SQLException e) {
            LOGGER.info("Can`t insert book {}", e);
            throw e;
        }
    }

    public static Book getBookById(Connection connection, int bookId) throws SQLException {
        LOGGER.debug("JdbcService get book by id {}", bookId);
        String findQuery = "select * from books where id = ?";
        Book book = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    book = new Book(bookId,
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get book by id {}", e);
            throw e;
        }
        return book;
    }

    public static List<Book> getAllBooks(Connection connection) throws SQLException{
        LOGGER.debug("JdbcService get all books");
        String findQuery = "select * from books";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Book book = new Book(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                books.add(book);
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get all books {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksUseLimit(Connection connection, int limit) throws SQLException {
        LOGGER.debug("JdbcService get books use limit {}", limit);
        String findQuery = "select * from books limit ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setInt(1, limit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get books use limit {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksWithGivenTitle(Connection connection, String findTitle) throws SQLException {
        LOGGER.debug("JdbcService get books with title {}", findTitle);
        String findQuery = "select * from books where title=?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setString(1, findTitle);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get books with title {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksWithStartsTitleSubstring(Connection connection, String findString) throws SQLException {
        LOGGER.debug("JdbcService get books by start title substring {}", findString);
        String findQuery = "select * from books where title like ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setString(1, findString + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get book by start title substring {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksWithEndsTitleSubstring(Connection connection, String findString) throws SQLException {
        LOGGER.debug("JdbcService get books by end title substring {}", findString);
        String findQuery = "select * from books where title like ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setString(1, "%" + findString);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get books by end title substring {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksWithYearsInfo(Connection connection, int startYear, int endYear) throws SQLException{
        LOGGER.debug("JdbcService get books with years info");
        String findQuery = "select * from books where year between ? and ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setInt(1, startYear);
            preparedStatement.setInt(2, endYear);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get books with years info {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksWithPagesInfo(Connection connection, int startPage, int endPage)throws SQLException {
        LOGGER.debug("JdbcService get books with pages info");
        String findQuery = "select * from books where pages between ? and ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setInt(1, startPage);
            preparedStatement.setInt(2, endPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get books with pages info {}", e);
            throw e;
        }
        return books;
    }

    public static List<Book> getBooksWithPagesAndYearInfo(Connection connection,
                                                          int startYear, int endYear, int startPage, int endPage) throws SQLException{
        LOGGER.debug("JdbcService get books with pages between {} and {} and year between {} and {}",
                startPage, endPage, startYear, endYear);
        String findQuery = "select * from books where pages between ? and ? and year between ? and ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setInt(1, startPage);
            preparedStatement.setInt(2, endPage);
            preparedStatement.setInt(3, startYear);
            preparedStatement.setInt(4, endYear);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Can`t get books with page and year section", e);
            throw e;
        }
        return books;
    }

    public static void updateBooksPages(Connection connection, int pages)throws SQLException {
        LOGGER.debug("JdbcService update all books by page amount {}", pages);
        String updateQuery = "update books set pages = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, pages);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t update all books by page amount {}", e);
            throw e;
        }
    }

    public static void updateBooksByPageLowerBorder(Connection connection, int lowerPageBorder, int newPages) throws SQLException{
        LOGGER.debug("JdbcService update book by page lower border {}", lowerPageBorder);
        String updateQuery = "update books set pages = ? where pages > ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, newPages);
            preparedStatement.setInt(2, lowerPageBorder);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t update books by lower page border {}", e);
            throw e;
        }
    }

    public static void updateBooksByPageAndYearInfo(Connection connection, int newPages, int oldPages, int year)throws SQLException {
        LOGGER.debug("Jdbc service update books by pages and year info");
        String updateQuery = "update books set pages = ? where pages > ? and year < ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, newPages);
            preparedStatement.setInt(2, oldPages);
            preparedStatement.setInt(3, year);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t update update books by pages and year info {}", e);
            throw e;
        }
    }

    public static void updateBooksByPagesAndTitleInfo(Connection connection,
                                                      int newPages, int oldPages, String startSubstring)throws SQLException
    {
        LOGGER.debug("Jdbc service update books by pages and title info");
        String updateQuery = "update books set pages = ? where pages > ? and title like ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, newPages);
            preparedStatement.setInt(2, oldPages);
            preparedStatement.setString(3, startSubstring + "%");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t update update books by pages and title info {}", e);
            throw e;
        }
    }

    public static void deleteBooksByPageLowerBorder(Connection connection, int pages) throws SQLException{
        LOGGER.debug("JdbcService delete books by pages lower border {}", pages);
        String deleteQuery = "delete from books where pages > ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, pages);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t delete books by pages lower border {}", e);
            throw e;
        }
    }

    public static void deleteBooksWithTitleInfo(Connection connection, String startSubstring)throws SQLException {
        LOGGER.debug("JdbcService delete books by start title substring {}", startSubstring);
        String deleteQuery = "delete from books where title like ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setString(1, startSubstring + "%");
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t delete books by start title substring {}", e);
            throw e;
        }
    }

    public static void deleteBooksWithIds(Connection connection, int startId, int endId)throws SQLException {
        LOGGER.debug("JdbcService delete books with ids between {} and {}", startId, endId);
        String deleteQuery = "delete from books where id between ? and ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, startId);
            statement.setInt(2, endId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t delete books with ids borders {}", e);
            throw e;
        }
    }

    public static void deleteBooksByTitle(Connection connection, String title)throws SQLException {
        LOGGER.debug("JdbcService delete books by title {}", title);
        String deleteQuery = "delete from books where title = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setString(1, title);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t delete books by title {}", e);
            throw e;
        }
    }

    public static void deleteBooksWithTitles(Connection connection,
                                             String firstTitle, String secondTitle, String thirdTitle)throws SQLException {
        LOGGER.debug("JdbcService delete books by titles {}, {}, {}", firstTitle, secondTitle, thirdTitle);
        String deleteQuery = "delete from books where title = ? or title = ? or title = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setString(1, firstTitle);
            statement.setString(2, secondTitle);
            statement.setString(3, thirdTitle);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t delete books by titles {}", e);
            throw e;
        }
    }

    public static void deleteAllBooks(Connection connection)throws SQLException {
        LOGGER.debug("JdbcService delete all books");
        String deleteQuery = "delete from books";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t delete all books {}", e);
            throw e;
        }
    }

    public static void setNoBindingType(Connection connection) throws SQLException{
        LOGGER.debug("JdbcSerivce Set no binding type");
        String query = "update books set binding= 'no'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t update books binding type", e);
            throw e;
        }
    }

    public static void updateBooksPublishingHouse(Connection connection,
                                                  int startId, int endId, String publishingHouse) throws SQLException{
        LOGGER.debug("JdbcService update books publishing house");
        String updateQuery = "update books set publishing_house= ? where id between ? and ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, publishingHouse);
            preparedStatement.setInt(2, startId);
            preparedStatement.setInt(3, endId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t update update books publishing house {}", e);
            throw e;
        }
    }

    public static void changeTitleColName(Connection connection, String oldName, String newName)throws SQLException {
        LOGGER.debug("JdbcService change title column name to {}", newName);
        String query = "alter table books change " + oldName + " " + newName + " varchar(50)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.info("Can`t change title column name {}", e);
            throw e;
        }
    }

    public static void changeTableName(Connection connection, String oldName, String newName)throws SQLException {
        LOGGER.debug("JdbcService change books table name to {}", newName);
        String query = "rename table " + oldName + " to " + newName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.info("Can`t change book table name {}", e);
            throw e;
        }
    }

    public static void dropTable(Connection connection, String table)throws SQLException {
        LOGGER.debug("JdbcService drop table {}", table);
        String query = "drop table " + table;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Can`t drop table {}", e);
            throw e;
        }
    }
}
