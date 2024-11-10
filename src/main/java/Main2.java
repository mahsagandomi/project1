import Exceptions.CustomerBirthDateException;
import Exceptions.CustomerNationalIdException;
import model.CustomersImpl;
import org.apache.log4j.Logger;
import repository.DatabaseMethods;
import utility.CustomerJsonReport;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main2: This class serves as the second entry point for the application,
 * focused on generating a JSON report of customer data from the database.
 * It retrieves customer data using a ResultSet from the DatabaseMethods class
 * and utilizes the CustomerJsonReport class to write this data into a JSON file.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class Main2 {
    // Logger instance for logging error messages
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(Main2.class);

    /**
     * The main method that starts the JSON report generation process.
     */

    public static void main(String[] args) {
        // Specify the file path for the output JSON file
        File file = new File("result.json");
        // Create a list to store customer objects
        List<CustomersImpl> list = new ArrayList<>();

        try {
            // Retrieve a ResultSet containing customer data from the database
            ResultSet resultSet = DatabaseMethods.getResultsetReport();
            // Generate JSON output from the database using the specified file and customer list
            CustomerJsonReport.writeJsonFile(file, list, resultSet);
        } catch (SQLException | CustomerBirthDateException | IOException | CustomerNationalIdException e) {
            // Log any SQLExceptions, CustomerBirthDateExceptions, IOException, or CustomerNationalIdExceptions that occur
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
