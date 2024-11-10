package repository.sqlsquery;

/**
 * SqlQueries: This class contains static final string  SQL queries to use for other methods.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class SqlQueries {

    // SQL query to retrieve customer and account  where account balance is greater than 1000
    public static final String AC_REPORT_SQL_QUERY = "select * from customers right join accounts on customerId=accountCustomerId where accountBalance>1000";
    // SQL query to check if a customer with the given customer ID exists in the database
    public static final String CU_ID_IS_EXIST_QUERY = "select * from customers where customerId = ?";
    // SQL query to insert a new customer into the customers table
    public static final String WRITE_CU_QUERY = "INSERT INTO customers (customerId, customerName, customerSubName, customerAddress, customerZipcode, customerNationalId, customerBirthDate) VALUES (?,?,?,?,?,?,?)";
    // SQL query to insert a new account into the accounts table
    public static final String WRITE_AC_QUERY = "Insert Into accounts(accountNumber, accountType, accountCustomerId, accountLimit, accountOpenDate, accountBalance) values (?,?,?,?,?,?)";

    // Private constructor to don't allow to create object from this class
    private SqlQueries() {
    }
}
