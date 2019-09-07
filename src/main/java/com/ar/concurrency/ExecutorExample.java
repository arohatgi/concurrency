package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executor;

public class ExecutorExample implements Executor {
    private static Logger logger = LogManager.getLogger(ExecutorExample.class.getSimpleName());

    public static void main(String[] args) {
        logger.debug("Executor run successfully!!");
        Executor executor = new ExecutorExample();
        executor.execute(ExecutorExample::long_running_task);
        logger.info("Exiting Executor application.");
    }

    private static void long_running_task() {
        for (int i = 0; i < 10; i++) {
            logger.info("Test Thread invoker execution [" + (i + 1) + "].");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}