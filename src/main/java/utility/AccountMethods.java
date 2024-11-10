package utility;

import model.AccountType;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * AccountMethods: This class provides methods for reading account data from a CSV file,
 * validating the data, and inserting it into a database.
 * <p>
 * It uses Apache Commons CSV for parsing CSV files and Log4j for logging warnings
 * during data validation processes.
 * </p>
 *
 * <p>
 * The class includes methods to validate account numbers, account types, and account balances,
 * ensuring that only valid data is written to the database.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class AccountMethods {


    // Private constructor to don't allow to create object from this class
    private AccountMethods() {
    }

    // Logger instance for logging warnings and information
    private static final Logger LOGGER = Logger.getLogger(AccountMethods.class);

    /**
     * Reads account data from a specified CSV file, validates the data, and writes
     * valid records to the database.
     *
     * @throws IOException  If an error occurs while reading the CSV file.
     * @throws SQLException If an SQL error occurs during database operations.
     */

    public static void readAccountFile() throws IOException, SQLException, SchedulerException, InterruptedException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        int numJobs = 2; // تعداد Jobها

        // ایجاد و اضافه کردن Jobها و Triggerها به Scheduler با استفاده از حلقه
        //   int startRecord =1;
        // int endRecord = 5;
        int recordsPerJob = 5; // تعداد رکوردها برای هر Job
        for (int i = 0; i < numJobs; i++) {
            int startRecord = i * recordsPerJob + 1;
            int endRecord = startRecord + recordsPerJob - 1;
            JobDetail jobDetail = createJob("ACsvJob" + (i + 1), "group1", startRecord, endRecord);

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger" + (i + 1), "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withRepeatCount(0))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            //startRecord=endRecord+1;
            //endRecord=startRecord+5;

        }

        // start Scheduler
        scheduler.start();

        LOGGER.info("Finished scheduling jobs for reading and saving customer data from file");
        //2 minutes
        Thread.sleep(60 * 1000);

        // ending Scheduler
        scheduler.shutdown(true);
        System.out.println("Scheduler has been shut down.");


    }
    /**
     * Creates a JobDetail with the specified parameters.
     *
     * @param jobName    The name of the job
     * @param groupName  The group name of the job
     * @param startRecord The start record number for the job
     * @param endRecord  The end record number for the job
     * @return JobDetail instance
     */

    private static JobDetail createJob(String jobName, String groupName, int startRecord, int endRecord) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("startRecord", startRecord);
        dataMap.put("endRecord", endRecord);

        return JobBuilder.newJob(AccountCsvThread.class)
                .withIdentity(jobName, groupName)
                .usingJobData(dataMap)
                .build();
    }



    /**
     * Validates the account number format.
     *
     * @param accountNumber The account number to validate.
     * @return true if the account number is valid; false otherwise.
     */

    public static boolean validateAccountNumber(String accountNumber) {

        return accountNumber.matches("0\\d{21}");
    }

    /**
     * Validates the account type against accepted types.
     *
     * @param accountType The account type to validate.
     * @return true if the account type is valid; false otherwise.
     */
    public static boolean validateAccountType(AccountType accountType) {
        return accountType == AccountType.SAVINGS || accountType == AccountType.RECURRINGDEPOSIT || accountType == AccountType.FIXEDDEPOSITACCOUNT;

    }

    /**
     * Validates if the account balance does not exceed the specified account limit.
     *
     * @param accountLimit   The maximum allowable balance for the account.
     * @param accountBalance The current balance of the account to validate.
     * @return true if the account balance is valid; false otherwise.
     */

    public static boolean validateAccountBalance(int accountLimit, int accountBalance) {
        return accountBalance <= accountLimit;
    }

}
