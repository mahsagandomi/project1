package Threads;

import model.AccountType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import repository.DatabaseMethods;
import utility.AccountMethods;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * AccountCsvThread class is a Quartz Job that processes a subset of account records from a CSV file.
 * The job reads account data within a specific range, validates key fields (account number, type, customer ID, balance),
 * and saves valid records to the database. Logging is used extensively for tracking the process, reporting errors, and warnings.
 * Dependencies:
 * - Apache Commons CSV: for parsing CSV data.
 * - Quartz: for job scheduling and execution.
 * - Log4j: for logging process steps, errors, and validation issues.
 * This class is useful for processing large account data files in a multithreading, scheduled manner, allowing records
 * to be processed in chunks for efficiency and modularity.
 *
 * @author mahsa
 */

public class AccountCsvThread implements Job {
    // Logger instance for recording process and error logs
    private static final Logger LOGGER = Logger.getLogger(AccountCsvThread.class);

    /**
     * Executes the job to process a specific range of account records in the CSV file.
     * The job reads each record, validates key fields, and writes valid records to the database.
     *
     * @param jobExecutionContext The context in which the job is executed, containing job details like start and end record numbers.
     */

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // Retrieve start and end record indices from JobDataMap for processing
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        int startRecord = dataMap.getInt("startRecord");
        int endRecord = dataMap.getInt("endRecord");
        LOGGER.info("Job started: Processing records from " + startRecord + " to " + endRecord);
        // Read the CSV file
        Reader inAccounts;
        try {
            inAccounts = new FileReader("src/main/java/csvfiles/Accounts.csv");

            Iterable<CSVRecord> accountRecords;

            accountRecords = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(inAccounts);
            // Iterate over each record in the CSV file
            int currentIndex = 0;
            for (CSVRecord records : accountRecords) {
                currentIndex++;
                // Skip records outside of start and end range
                if (currentIndex < startRecord) {
                    continue;
                }
                if (currentIndex > endRecord) {
                    break;
                }
                String accountNumber = records.get(1);
                // Validate the account number format
                if (AccountMethods.validateAccountNumber(accountNumber)) {
                    LOGGER.info("Valid account number: " + accountNumber);
                } else {
                    // Log a warning if the account number is invalid
                    LOGGER.warn("invalid AccountNumber");
                    continue;
                }
                // Retrieve and validate the account type from the CSV record
                AccountType accountType = AccountType.valueOf(records.get(2));
                if (AccountMethods.validateAccountType(accountType)) {
                    LOGGER.info("Valid account type: " + accountType);
                    // Valid account type, proceed
                } else {
                    // Log a warning if the account type is invalid
                    LOGGER.warn("invalid AccountType");
                    continue;
                }
                // Retrieve the customer ID and check its existence in the database
                int accountCustomerId = Integer.parseInt(records.get(3));

                if (DatabaseMethods.customerIdIsExist(accountCustomerId)) {
                    LOGGER.info("Customer ID exists: " + accountCustomerId);
                    // Customer ID exists, proceed
                } else {
                    // Log a warning if the customer ID does not exist
                    LOGGER.warn("customerId is not exist");
                    continue;
                }

                // Retrieve account limit and balance
                int accountLimit = Integer.parseInt(records.get(4));
                int accountBalance = Integer.parseInt(records.get(6));
                // Validate the account balance against the limit
                if (AccountMethods.validateAccountBalance(accountLimit, accountBalance)) {
                    LOGGER.info("Valid account balance for account: " + accountNumber);
                    // Valid account balance, proceed to write the account
                } else {
                    // Log a warning if the account balance exceeds the limit
                    LOGGER.warn("invalid AccountBalance");
                    continue;
                }
                // Parse the account open date
                LocalDate accountOpenDate = LocalDate.parse(records.get(5));
                // Write the validated account information to the database
                DatabaseMethods.writeAccountsToDatabase(accountNumber, accountType, accountCustomerId,
                        accountLimit, accountOpenDate,
                        accountBalance);


            }
            // Log a message indicating that the account data reading process has finished
            LOGGER.info("finished reading account data from file");
            // Log a message indicating that the account data has been successfully written to the database
            LOGGER.info("Account written to database");

        } catch (SQLException | IOException e) {
            // Log the error message to the logger in case of a SQL or IO exception
            LOGGER.error(e.getMessage());
            // Print the error message to the console for additional debugging
            System.out.println(e.getMessage());

        }
    }
}