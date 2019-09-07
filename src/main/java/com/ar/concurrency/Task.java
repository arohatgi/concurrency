package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class Task implements Runnable {
    private static Logger logger = LogManager.getLogger(Task.class.getSimpleName());
    private String taskId;

    // default constructore
    public Task() {

        taskId = "Default Task";
    }

    // constructor with task Id parameter
    public Task(String taskId) {

        this.taskId = taskId;
    }

    public static void main(String[] args) {
        // Future execution Thread Pool
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> {
            logger.info("Waiting...");
//            Thread.sleep(3000L);
            return "Hello World";
        });
        try {
            String rs = future.get(1, TimeUnit.SECONDS);
            logger.info("Result from future : " + rs);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        // Future execution Thread Pool
        // fixed Thread Pool
        runFixedThreadPool();
        // schedule Thread Pool
        runScheduledThreadPool();
    }

    private static void runFixedThreadPool() {
        // fixed Thread Pool
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(new Task("fixed"));
        }
        executor.shutdown();
    }

    private static void runScheduledThreadPool() {
        // schedule Thread Pool
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < 3; i++) {
            ScheduledFuture<?> scheduledFuture = scheduledExecutor.schedule(new Task("Single scheduled Thread"), 1L, TimeUnit.SECONDS);
        }
        try {
            scheduledExecutor.awaitTermination(15L, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        scheduledExecutor.shutdown();
    }

    void long_running_task() {

        for (int i = 0; i < 3; i++) {
            logger.info(String.format("Test Thread [Id: %s] invoker execution [%d].", taskId, i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        long_running_task();
    }
}