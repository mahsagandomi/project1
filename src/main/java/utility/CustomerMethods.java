package utility;

import org.apache.log4j.Logger;
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
     * @throws IOException      If an error occurs while reading the file
     * @throws SQLException     If an error occurs during database operations
     * @throws SchedulerException If an error occurs during scheduling jobs
     * @throws InterruptedException If the thread is interrupted
     */
    public static void readCustomerFile() throws IOException, SQLException, SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // تعداد Jobها و رکوردها در هر Job
        int numJobs = 2; // تعداد Jobها

        // ایجاد و اضافه کردن Jobها و Triggerها به Scheduler با استفاده از حلقه
        //   int startRecord =1;
        // int endRecord = 5;
        int recordsPerJob = 5; // تعداد رکوردها برای هر Job
        for (int i = 0; i < numJobs; i++) {
            int startRecord = i * recordsPerJob + 1;
            int endRecord = startRecord + recordsPerJob - 1;
            JobDetail jobDetail = createJob("CsvJob" + (i + 1), "group1", startRecord, endRecord);

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger" + (i + 1), "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withRepeatCount(0))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            //startRecord=endRecord+1;
            //endRecord=startRecord+5;
            Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
            LOGGER.info("Trigger " + trigger.getKey() + " state: " + state);
            System.out.println(state);

        }

        // start Scheduler
        scheduler.start();
        LOGGER.info("Finished scheduling jobs for reading and saving customer data from file");

        Thread.sleep(60 * 1000); // تنظیم برای متوقف کردن Scheduler بعد از ۶۰ ثانیه
        scheduler.shutdown(true); // توقف کامل Scheduler
        System.out.println("Scheduler has been shut down.");

        LOGGER.info("Finished reading and saving customer data from file");
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

        return JobBuilder.newJob(CustomerCsvThread.class)
                .withIdentity(jobName, groupName)
                .usingJobData(dataMap)
                .build();
    }

    /**
     * Validates the customer's national ID to ensure it consists of exactly 10 digits.
     *
     * @param customerNationalId The customer's national ID
     * @return true if the national ID is valid, false otherwise
     */
    public static boolean validateCustomerNationalID(String customerNationalId) {
        return customerNationalId.matches("[0-9]{10}");
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
