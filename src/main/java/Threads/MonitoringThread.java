package Threads;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * MonitoringThread: A custom JobListener implementation to monitor Quartz job execution.
 * This class logs important lifecycle events of jobs, such as when they are about to execute,
 * vetoed, or completed.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class MonitoringThread implements JobListener {
    // Logger instance for capturing job lifecycle events
    private static final Logger LOGGER = Logger.getLogger(MonitoringThread.class);

    /**
     * Provides a unique name for this JobListener instance.
     *
     * @return A string representing the listener's name.
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Triggered when a job is about to be executed.
     * Logs the details of the job before execution begins.
     *
     * @param context The JobExecutionContext containing job details.
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        //System.out.println("Job is about to be executed: " + context.getJobDetail().getKey());
        LOGGER.info("Job is about to be executed: " + context.getJobDetail().getKey());

    }

    /**
     * Triggered when a job's execution is vetoed by a trigger listener.
     * Logs the details of the vetoed job.
     *
     * @param context The JobExecutionContext containing job details.
     */

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        //System.out.println("Job execution was vetoed: " + context.getJobDetail().getKey());
        LOGGER.info("Job execution was vetoed: " + context.getJobDetail().getKey());
    }

    /**
     * Triggered after a job has been executed, regardless of its outcome.
     * Logs the job completion and captures any exception that occurred during execution.
     *
     * @param context      The JobExecutionContext containing job details.
     * @param jobException The exception thrown during job execution, if any (can be null).
     */

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        //System.out.println("Job finished: " + context.getJobDetail().getKey());
        LOGGER.info("Job finished: " + context.getJobDetail().getKey());
        if (jobException != null) {
            //System.out.println("Job failed with exception: " + jobException.getMessage());
            LOGGER.warn("Job failed with exception: " + jobException.getMessage());
        }
    }
}

