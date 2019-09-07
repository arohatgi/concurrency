package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class MultiThreadedAsyncCalculator {
    private static Logger          logger   = LogManager.getLogger(MultiThreadedAsyncCalculator.class.getCanonicalName());
    private        ExecutorService executor = Executors.newFixedThreadPool(2);
    private        String          calcId;

    public MultiThreadedAsyncCalculator(String calcId) {
        this.calcId = calcId;
    }

    // Driver code
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        MultiThreadedAsyncCalculator asyncCalc = new MultiThreadedAsyncCalculator("async");
        Future<Integer>              future    = asyncCalc.square(10);
//        Integer         result    = checkLoop(future);
        Integer result = future.get(500, TimeUnit.MILLISECONDS);
        logger.info(String.format("result : %d", result));
        asyncCalc.shutdown();
    }

    private static Integer checkLoop(Future<Integer> future) throws InterruptedException, ExecutionException {
        while (!future.isDone()) {
            logger.info("Calculating...");
            Thread.sleep(200);
        }
        return future.get();
    }

    public void shutdown() {
        executor.shutdown();
    }

    public Future<Integer> square(Integer input) {
        return executor.submit(() -> getSquare(input));
    }

    private Integer getSquare(Integer input) throws InterruptedException {
        Thread.sleep(1000L);
        Integer result = input * input;
        logger.info(result);
        return result;
    }
}
