package utility;

import Exceptions.CustomerBirthDateException;
import Exceptions.CustomerNationalIdException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.CustomersImpl;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * AccountJsonReport: This utility class provides methods for writing customer data
 * to a JSON file.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerJsonReport {
    // Logger instance for logging
    private static final Logger LOGGER = Logger.getLogger(CustomerJsonReport.class);


    // Private constructor to don't allow to create object from this class
    private CustomerJsonReport() {
    }

    /**
     * writeJsonFile: This method retrieves customer data from a ResultSet and writes it
     * to a specified JSON file.
     *
     * @param file      The JSON file to write the customer data to.
     * @param list      A list of Customers to be populated with data from the ResultSet.
     * @param resultSet The ResultSet containing customer data retrieved from the database.
     * @throws SQLException                If an SQL error occurs while accessing the ResultSet.
     * @throws CustomerBirthDateException  If the birthdate of a customer is invalid.
     * @throws CustomerNationalIdException If the national ID of a customer is invalid.
     * @throws IOException                 If an error occurs during file writing.
     */
    public static void writeJsonFile(File file, List<CustomersImpl> list, ResultSet resultSet) throws SQLException, CustomerBirthDateException, CustomerNationalIdException, IOException {
        LOGGER.info("Starting to write customer data to JSON file");
        // Create an ObjectMapper instance to handle JSON serialization
        ObjectMapper objectMapper = new ObjectMapper();
        // the JavaTimeModule to support Java 8 date/time types
        objectMapper.registerModule(new JavaTimeModule());
        // Iterate through the ResultSet to read customer data
        while (resultSet.next()) {
            // Read fields from the customer table
            int customerId = resultSet.getInt("customerId");
            String customerName = resultSet.getString("customerName");
            String customerSubName = resultSet.getString("customerSubName");
            String customerAddress = resultSet.getString("customerAddress");
            int customerZipcode = resultSet.getInt("customerZipcode");
            String customerNationalId = resultSet.getString("customerNationalId");
            LocalDate customerBirthDate = resultSet.getDate("customerBirthDate").toLocalDate();
            // Create a new Customer object and add it to the list
            list.add(new CustomersImpl(customerId, customerName, customerSubName, customerAddress, customerZipcode, customerNationalId, customerBirthDate));
            LOGGER.info("Added customer" + customerId);
        }
        // Write the list of Customers to JSON file
        objectMapper.writeValue(file, list);
        LOGGER.info("Successfully wrote customer data to JSON file.");
        System.out.println("Json file created successfully");
    }


}
