package repository;

import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * SingleTon: This class used for  the database connection.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class SingleTon {

    private static final Logger LOGGER = Logger.getLogger(SingleTon.class);
    // Holds the single instance of the database connection
    private static Connection con;

    // Private constructor to prevent instantiation from outside the class
    private SingleTon() {
    }

    /**
     * get con: This method returns the single instance of the database connection.
     * If the connection does not exist, it creates a new one using the provided database credentials.
     *
     * @return Connection  the database connection
     */
    public static Connection getCon() {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        // Set the password used for decryption of the database password
        basicTextEncryptor.setPassword("MysqlPass");
        // Load the database configuration from the DB.properties file
        ResourceBundle resourceBundle = ResourceBundle.getBundle("DB");
        // JDBC URL for the database
        String url = resourceBundle.getString("jdbc.url");
        // Database username
        String username = resourceBundle.getString("jdbc.username");
        // Encrypted database password
        String password = resourceBundle.getString("jdbc.password");
        // Decrypt the password
        String decryptPass = basicTextEncryptor.decrypt(password);
        // Check if the connection is already
        if (con == null) {
            try {
                // Create a new connection using the JDBC URL, username, and decrypted password
                con = DriverManager.getConnection(url, username, decryptPass);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                System.out.println("sqlException");
            }
        }
        // Return the connection
        LOGGER.info("connection is successfully ");
        return con;
    }
}