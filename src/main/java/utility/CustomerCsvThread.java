package utility;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.quartz.*;
import repository.DatabaseMethods;

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
            int currentIndex=0;
            for (CSVRecord record : records) {
                currentIndex++;

                // Skip records outside of start and end range
                if (currentIndex < startRecord) {
                    continue;
                }
                if (currentIndex > endRecord) {
                    break;
                }
//                int recordNumber = Integer.parseInt(record.get(0));
//                if (recordNumber >= startRecord && recordNumber <= endRecord) {
//                    // Process the record only if it's within the start and end record range
//                } else {
//                    break;
//                }
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
                // Validate the customer's national ID
                if (CustomerMethods.validateCustomerNationalID(customerNationalId)) {
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
                        customerZipCode, customerNationalId,
                        customerBirthDate);


                LOGGER.info("Customer ID " + customerId + " successfully written to the database.");


            }
        } catch (SQLException | IOException e) {
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
        }

    }
}

