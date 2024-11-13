package utility;

import Threads.CustomerCsvThread;
import Threads.MonitoringThread;
import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * CustomerMethods: A utility class responsible for handling customer-related operations.
 * This class provides methods to read customer data from a CSV file, validate the data,
 * and save valid customer records into a database. The class utilizes Apache Commons CSV
 * for parsing CSV files and logs any validation issues using Apache Log4j.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class CustomerMethods {
    // Logger instance for logging warnings and errors
    private static final Logger LOGGER = Logger.getLogger(CustomerMethods.class);

    /**
     * Reads a CSV file containing customer information and saves it to the database.
     * This method processes each record in the CSV file, validates the data,
     * and writes valid records to the database using the DatabaseMethods class.
     *
     * @throws IOException          If an error occurs while reading the file
     * @throws SQLException         If an error occurs during database operations
     * @throws SchedulerException   If an error occurs during scheduling jobs
     * @throws InterruptedException If the thread is interrupted
     */
    public static void readCustomerFile() throws IOException, SQLException, SchedulerException, InterruptedException {
        // Create a SchedulerFactory to manage the scheduling of jobs
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // Obtain a Scheduler instance from the factory
        Scheduler scheduler = schedulerFactory.getScheduler();
        // Define the number of jobs to schedule
        int numJobs = 2;
        // Define the number of records each job will process
        int recordsPerJob = 5;
        // Loop to create and schedule jobs for processing customer data
        for (int i = 0; i < numJobs; i++) {
            // Calculate the range of records for the current job
            int startRecord = i * recordsPerJob + 1;
            int endRecord = startRecord + recordsPerJob - 1;
            // Create a JobDetail object for the current job
            JobDetail jobDetail = createJob("CustomerCsvFile" + (i + 1), startRecord, endRecord);
            // Create a Trigger to start the job immediately with no repetition
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger" + (i + 1), "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withRepeatCount(0))
                    .build();
            // Schedule the job with the scheduler using the JobDetail and Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            // Add a custom job listener to monitor the job's execution
            scheduler.getListenerManager().addJobListener(new MonitoringThread());
        }

        // start Scheduler
        scheduler.start();
        LOGGER.info("Finished scheduling jobs for reading and saving customer data from file");

        Thread.sleep(60 * 1000);
        scheduler.shutdown(true);
        System.out.println("Scheduler has been shut down.");

        LOGGER.info("Finished reading and saving customer data from file");
    }

    /**
     * Creates a JobDetail with the specified parameters.
     *
     * @param jobName     The name of the job
     * @param startRecord The start record number for the job
     * @param endRecord   The end record number for the job
     * @return JobDetail instance
     */
    private static JobDetail createJob(String jobName, int startRecord, int endRecord) {
        // Create a JobDataMap to store additional data to be passed to the job
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("startRecord", startRecord);
        dataMap.put("endRecord", endRecord);
        // Build and return a JobDetail object
        return JobBuilder.newJob(CustomerCsvThread.class)
                // Set the job name and group name
                .withIdentity(jobName, "group1")
                // Attach the data map containing job-specific data
                .usingJobData(dataMap)
                //build
                .build();
    }

    /**
     * Validates the customer's national ID to ensure it consists of exactly 10 digits.
     *
     * @param customerNationalId The customer's national ID
     * @return true if the national ID is valid, false otherwise
     */
    public static boolean validateCustomerNationalID(String customerNationalId) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
//        basicTextEncryptor.setPassword(basicTextEncryptor.decrypt(ConfigLoader.getEncryptionPassword()));
        basicTextEncryptor.setPassword(ConfigLoader.getEncryptionPassword());
        String customerNationalIdEncrypt = basicTextEncryptor.decrypt(customerNationalId);
        return customerNationalIdEncrypt.matches("[0-9]{10}");
    }

    /**
     * Validates the customer's birthdate to ensure it is after the year 1995.
     *
     * @param customerBirthDate The customer's birthdate
     * @return true if the birthdate is valid, false otherwise
     */
    public static boolean validateCustomerBirthDate(LocalDate customerBirthDate) {
        return customerBirthDate.getYear() > 1995;
    }
}
