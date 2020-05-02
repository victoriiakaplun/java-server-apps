package ru.omsu.imit.database.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class JdbcUtils {
    private static Connection connection;
    private static final String USER = "root";
    private static final String PASSWORD = "test";
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtils.class);

    public static boolean createConnection() {
        LOGGER.debug("Creating database connection");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.info("Can`t create connection");
            return false;
        }
    }

    public static Connection getConnection() {
            return connection;
        }

        public static void closeConnection() {
            LOGGER.debug("Closing database connection");
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info("Can`t close connection");
            }
        }
}
