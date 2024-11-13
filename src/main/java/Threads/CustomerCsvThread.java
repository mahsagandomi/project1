package Threads;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;
import org.quartz.*;
import repository.DatabaseMethods;
import utility.ConfigLoader;
import utility.CustomerMethods;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * CustomerCsvThread class is a Quartz Job that processes a subset of customer records from a CSV file.
 * The job retrieves customer data within a specific range, validates key fields (National ID, Birthdate),
 * and saves valid records to the database. Logging is used for tracking the process and reporting errors.
 * Dependencies:
 * - Apache Commons CSV: for CSV parsing.
 * - Quartz: for job scheduling and execution.
 * - Log4j: for logging information.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class CustomerCsvThread implements Job {


    // Logger instance for recording process and error logs
    private static final Logger LOGGER = Logger.getLogger(CustomerMethods.class);

    /**
     * Default constructor
     */
    public CustomerCsvThread() {
    }


    /**
     * Executes the job to process a specific range of customer records in the CSV file.
     * The job reads each record, validates key fields, and writes valid records to the database.
     *
     * @param jobExecutionContext The context in which the job is executed, containing job details like start and end record numbers.
     */

    @Override

    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // Retrieve the start and end record indices for processing
        int startRecord = dataMap.getInt("startRecord");
        int endRecord = dataMap.getInt("endRecord");
        LOGGER.info("Job started: Processing records from " + startRecord + " to " + endRecord);
        // Initialize the CSV reader
        Reader in;
        try {


            in = new FileReader("src/main/java/csvfiles/Customers.csv");

            // Reading the CSV file using Apache Commons CSV with the first record as a header
            Iterable<CSVRecord> records;

            records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            // Iterate over each record within the specified range
            int currentIndex = 0;
            for (CSVRecord record : records) {
                currentIndex++;

                // Skip records outside of start and end range
                if (currentIndex < startRecord) {
                    continue;
                }
                if (currentIndex > endRecord) {
                    break;
                }
                // Extract customer information from the CSV record
                // Customer ID
                int customerId = Integer.parseInt(record.get(1));
                // Customer first name
                String customerName = record.get(2);
                // Customer surname
                String customerSubName = record.get(3);
                // Customer address
                String customerAddress = record.get(4);
                // Customer zip code
                int customerZipCode = Integer.parseInt(record.get(5));
                // Customer national ID
                String customerNationalId = record.get(6);
                // Create an instance of BasicTextEncryptor to handle text encryption
                BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                // Set the encryption password by fetching it from the ConfigLoader
                basicTextEncryptor.setPassword(ConfigLoader.getEncryptionPassword());
                // Encrypt the customer’s national ID using the encryptor
                String customerNationalIdEncrypt = basicTextEncryptor.encrypt(customerNationalId);
                // Validate the customer's national ID
                if (CustomerMethods.validateCustomerNationalID(customerNationalIdEncrypt)) {
                    LOGGER.info("Valid national ID for customer: " + customerId);
                } else {
                    // Log a warning if the national ID is invalid
                    LOGGER.warn("invalid customerNationalID");
                    continue;
                }
                // Convert birthdate from string to LocalDate
                LocalDate customerBirthDate = LocalDate.parse(record.get(7));
                // Validate the customer's birthdate
                if (CustomerMethods.validateCustomerBirthDate(customerBirthDate)) {
                    LOGGER.info("Valid birthdate for customer: " + customerId);
                } else {
                    // Log a warning if the birthdate is invalid
                    LOGGER.warn("invalid customerBirthDate");
                    continue;
                }
                // Save customer information to the database
                DatabaseMethods.writeCustomersToDatabase(customerId, customerName,
                        customerSubName, customerAddress,
                        customerZipCode, customerNationalIdEncrypt,
                        customerBirthDate);

                // Log an informational message indicating that the customer ID has been successfully written to the database
                LOGGER.info("Customer ID " + customerId + " successfully written to the database.");


            }
        } catch (SQLException | IOException e) {
            // Log an error message if an exception (either SQLException or IOException) occurs
            LOGGER.error(e.getMessage());
            // Print the exception message to the console for debugging or user feedback
            System.out.println(e.getMessage());
        }

    }
}

