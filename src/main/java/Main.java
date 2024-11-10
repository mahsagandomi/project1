
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import utility.AccountMethods;
import utility.CustomerMethods;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main: The entry point of the application.
 * This class serves as the main execution point for the program,
 * where it initializes and processes customer and account data
 * from CSV files. It utilizes methods from the CustomerMethods
 * and AccountMethods utility classes to read and process the
 * respective CSV files. Any exceptions that occur during
 * file reading or database operations are logged for debugging
 * purposes.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class Main {
    // Logger instance for logging error messages
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    /**
     * The main method that starts the application.
     */

    public static void main(String[] args) {

        try {
            // Attempt to read customer data from the specified CSV file
            CustomerMethods.readCustomerFile();
        } catch (SQLException | IOException | SchedulerException | InterruptedException e) {
            // Log any SQLException or IOException ,...
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        try {
            // Attempt to read account data from the specified CSV file
            AccountMethods.readAccountFile();
        } catch (SQLException | IOException | SchedulerException | InterruptedException e) {
            // Log any SQLException or IOException ,...
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}