
import model.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.DatabaseMethods;
import repository.queries.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the  DatabaseMethods class.
 * This class uses Mockito to mock database operations and validate the behavior of methods responsible for interacting with the database.
 * Key Features Tested:
 * - Writing customer and account data to the database.
 * - Verifying the existence of a customer ID in the database.
 * - Retrieving reports using a query.
 * - Ensuring the correct invocation of SQL statements and proper setting of parameters.
 */

class DatabaseMethodsTest {
    // Mocked database connection
    @Mock
    private Connection mockConnection;
    // Mocked PreparedStatement
    @Mock
    private PreparedStatement mockPreparedStatement;
    // Mocked ResultSet
    @Mock
    private ResultSet mockResultSet;

    /**
     * Initializes mock objects and injects them into  DatabaseMethods.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject mock connection into DatabaseMethods
        DatabaseMethods.con = mockConnection;
    }

    /**
     * Tests that customer data is written to the database correctly.
     * Verifies the correct SQL parameters are set and that the statement executes successfully.
     */
    @Test
    void testWriteCustomersToDatabase() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(SqlQueries.WRITE_CU_QUERY)).thenReturn(mockPreparedStatement);
        DatabaseMethods.setConnection(mockConnection); // تزریق اتصال mock
        int customerId = 11;
        String customerName = "John";
        String customerSurName = "Doe";
        String customerAddress = "123 Main St";
        int customerZipCode = 12345;
        String customerNationalId = "123456789";
        LocalDate customerBirthDate = LocalDate.of(1990, 1, 1);

        // Act
        DatabaseMethods.writeCustomersToDatabase(customerId, customerName, customerSurName,
                customerAddress, customerZipCode, customerNationalId, customerBirthDate);

        // Assert
        verify(mockPreparedStatement).setInt(1, customerId);
        verify(mockPreparedStatement).setString(2, customerName);
        verify(mockPreparedStatement).setString(3, customerSurName);
        verify(mockPreparedStatement).setString(4, customerAddress);
        verify(mockPreparedStatement).setInt(5, customerZipCode);
        verify(mockPreparedStatement).setString(6, customerNationalId);
        verify(mockPreparedStatement).setDate(7, java.sql.Date.valueOf(customerBirthDate));
        verify(mockPreparedStatement).executeUpdate();
    }

    /**
     * Tests that the method correctly checks if a customer ID exists in the database.
     * Ensures the query executes and returns the expected result.
     */

    @Test
    void testCustomerIdIsExist() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(SqlQueries.CU_ID_IS_EXIST_QUERY)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        DatabaseMethods.setConnection(mockConnection);

        int accountCustomerId = 2;

        // Act
        boolean result = DatabaseMethods.customerIdIsExist(accountCustomerId);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, accountCustomerId);
        verify(mockPreparedStatement).executeQuery();
    }

    /**
     * Tests that account data is written to the database correctly.
     * Validates parameter settings and ensures the statement is executed.
     */

    @Test
    void testWriteAccountsToDatabase() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(SqlQueries.WRITE_AC_QUERY)).thenReturn(mockPreparedStatement);
        DatabaseMethods.setConnection(mockConnection); // تزریق اتصال mock
        String accountNumber = "0123456789000000000000";
        AccountType accountType = AccountType.SAVINGS;
        int accountCustomerId = 1;
        int accountLimit = 1000;
        LocalDate accountOpenDate = LocalDate.now();
        int accountBalance = 5000;

        // Act
        DatabaseMethods.writeAccountsToDatabase(accountNumber, accountType, accountCustomerId,
                accountLimit, accountOpenDate, accountBalance);

        // Assert
        verify(mockPreparedStatement).setString(1, accountNumber);
        verify(mockPreparedStatement).setString(2, accountType.toString());
        verify(mockPreparedStatement).setInt(3, accountCustomerId);
        verify(mockPreparedStatement).setInt(4, accountLimit);
        verify(mockPreparedStatement).setDate(5, java.sql.Date.valueOf(accountOpenDate));
        verify(mockPreparedStatement).setInt(6, accountBalance);
        verify(mockPreparedStatement).executeUpdate();
    }

    /**
     * Tests that the method for retrieving a report returns a valid ResultSet.
     * Verifies that the SQL query is executed correctly.
     */

    @Test
    void testGetResultSetReport() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(SqlQueries.AC_REPORT_SQL_QUERY)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        DatabaseMethods.con = mockConnection;

        // Act
        ResultSet resultSet = DatabaseMethods.getResultsetReport();

        // Assert
        assertNotNull(resultSet);
        verify(mockPreparedStatement).executeQuery();
    }

    /**
     * Tests the invocation of the `prepareStatement` method on the mocked connection.
     * Ensures that the PreparedStatement is created correctly.
     */
    @Test
    void testPrepareStatementInvocation() throws SQLException {
        when(mockConnection.prepareStatement(SqlQueries.WRITE_CU_QUERY)).thenReturn(mockPreparedStatement);

        PreparedStatement ps = mockConnection.prepareStatement(SqlQueries.WRITE_CU_QUERY);

        assertNotNull(ps);
        verify(mockConnection).prepareStatement(SqlQueries.WRITE_CU_QUERY);
    }

}
