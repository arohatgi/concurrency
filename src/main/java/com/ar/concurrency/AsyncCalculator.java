package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class AsyncCalculator {
    private static Logger          logger   = LogManager.getLogger(AsyncCalculator.class.getCanonicalName());
    private        ExecutorService executor = Executors.newSingleThreadExecutor();
    private        String          calcId;

    public AsyncCalculator(String calcId) {
        this.calcId = calcId;
    }

    public void shutdown() {
        executor.shutdown();
    }

    // Driver code
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        AsyncCalculator asyncCalc = new AsyncCalculator("async");
        Future<Integer> future    = asyncCalc.square(10);
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

    public Future<Integer> square(Integer input) {
        return executor.submit(() -> getSquare(input));
    }

    private Integer getSquare(Integer input) throws InterruptedException {
        Thread.sleep(1000L);
        return input * input;
    }
}
