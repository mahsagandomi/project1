package repository;

import model.AccountType;
import org.apache.log4j.Logger;
import repository.sqlsquery.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * DatabaseMethods: This class provides methods for interacting with the database
 * related to customers and accounts. It includes functionalities for writing new
 * customer and account records, checking for existing customer IDs, and retrieving
 * reports of accounts.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class DatabaseMethods {
    // Logger instance for logging error messages
    private static final Logger LOGGER = Logger.getLogger(DatabaseMethods.class);

    // Static variable to hold the database connection
    static Connection con;
    // Private constructor to don't allow to create object from this class
    private DatabaseMethods() {
    }

    /**
     * Writes a new customer record into the database.
     *
     * @param customerId         The unique identifier for the customer.
     * @param customerName       The name of the customer.
     * @param customerSurName    The sub-name or additional name of the customer.
     * @param customerAddress    The address of the customer.
     * @param customerZipCode    The ZIP code for the customer's address.
     * @param customerNationalId The national ID of the customer.
     * @param customerBirthDate  The birthdate of the customer.
     * @throws SQLException If there is an error while executing the SQL statement.
     */
    public static void writeCustomersToDatabase(int customerId, String customerName,
                                      String customerSurName, String customerAddress,
                                      int customerZipCode, String customerNationalId,
                                      LocalDate customerBirthDate) throws SQLException {
        // Get the database connection
        con = SingleTon.getcon();
        // **گام 1.1**: خواندن رکوردهای فایل customers.csv
        PreparedStatement preparedStatement = con.prepareStatement(SqlQueries.WRITE_CU_QUERY);
        // Set customer ID
        preparedStatement.setInt(1, customerId);
        // Set customer name
        preparedStatement.setString(2, customerName);
        // Set customer sub name
        preparedStatement.setString(3, customerSurName);
        // Set customer address
        preparedStatement.setString(4, customerAddress);
        // Set customer ZIP code
        preparedStatement.setInt(5, customerZipCode);
        // Set customer national ID
        preparedStatement.setString(6, customerNationalId);
        // Set customer birthdate
        preparedStatement.setDate(7, java.sql.Date.valueOf(customerBirthDate));
        // Execute the SQL update statement
        preparedStatement.executeUpdate();
        LOGGER.info("Customer record written successfully: " +  customerNationalId );
    }

    /**
     * Checks if a customer ID already exists in the database.
     *
     * @param accountCustomerId The customer ID to check for existence.
     * @return true if the customer ID exists, false if not exists.
     * @throws SQLException If there is an error while executing the SQL statement.
     */
    public static boolean customerIdIsExist(int accountCustomerId) throws SQLException {
        // Prepare the SQL statement to check if the customer ID exists
        PreparedStatement preparedStatement1 = con.prepareStatement(SqlQueries.CU_ID_IS_EXIST_QUERY);
        preparedStatement1.setInt(1, accountCustomerId);
        // Execute the query and check if a result is returned
        ResultSet resultSet = preparedStatement1.executeQuery();
        // Returns true if a record exists
        return resultSet.next();

    }

    /**
     * Writes a new account record into the database.
     *
     * @param accountNumber     The account number.
     * @param accountType       The type of the account.
     * @param accountCustomerId The customer ID associated with the account.
     * @param accountLimit      The account limit.
     * @param accountOpenDate   The date the account was opened.
     * @param accountBalance    The current balance of the account.
     * @throws SQLException If there is an error while executing the SQL statement.
     */
    public static void writeAccountsToDatabase(String accountNumber, AccountType accountType, int accountCustomerId,
                                     int accountLimit, LocalDate accountOpenDate,
                                     int accountBalance) throws SQLException {
        // Get the database connection
        con = SingleTon.getcon();
        // Prepare the SQL statement for inserting a new account
        PreparedStatement preparedStatement = con.prepareStatement(SqlQueries.WRITE_AC_QUERY);

        // Set account number
        preparedStatement.setString(1, accountNumber);
        // Set account type
        preparedStatement.setString(2, accountType.toString());
        // Set customer ID
        preparedStatement.setInt(3, accountCustomerId);
        // Set account limit
        preparedStatement.setInt(4, accountLimit);
        // Set account open date
        preparedStatement.setDate(5, java.sql.Date.valueOf(accountOpenDate));
        // Set account balance
        preparedStatement.setInt(6, accountBalance);
        // Execute the SQL update statement
        preparedStatement.executeUpdate();
        LOGGER.info("Account record written successfully: " + accountNumber );

    }

    /**
     * Retrieves a report of accounts with a balance greater than 1000.
     *
     * @return ResultSet containing the report data.
     * @throws SQLException If there is an error while executing the SQL statement.
     */
    public static ResultSet getResultsetReport() throws SQLException {
        con = SingleTon.getcon();
        // Prepare the SQL statement for retrieving account report
        PreparedStatement preparedStatement = con.prepareStatement(SqlQueries.AC_REPORT_SQL_QUERY);
        LOGGER.info("Account result set report successfully.");
        // Execute the query and return the result set
        return preparedStatement.executeQuery();

    }

}
