package com.stolensugar.web.update;

import com.google.common.annotations.VisibleForTesting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for executing update tasks. The updater is created in an off state. A call to start will
 * start the publishing process.
 */
@Singleton
public class Updater {

    private final ScheduledExecutorService scheduledExecutorService;
    private final Runnable updateTask;
    private boolean isRunning;

    /**
     * Instantiates a new Updater object.
     *
     * @param scheduledExecutorService will schedule update tasks
     * @param updateTask the task that should be scheduled to update the database
     */
    @Inject
    public Updater(ScheduledExecutorService scheduledExecutorService,
                         Runnable updateTask) {
        this.updateTask = updateTask;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    /**
     * Start updating
     */
    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        scheduledExecutorService.scheduleWithFixedDelay(updateTask, 0, 1, TimeUnit.DAYS);
    }

    /**
     * Stop updating
     */
    public void stop() {
        isRunning = false;
        scheduledExecutorService.shutdown();
    }

    /**
     * Returns true if the updater is currently updating and false otherwise.
     * @return if the updater is currently processing.
     */
    @VisibleForTesting
    boolean isRunning() {
        return isRunning;
    }
}