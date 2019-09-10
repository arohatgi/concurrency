package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class MultiThreadedAsyncCalculator {
    private static Logger          logger = LogManager.getLogger(MultiThreadedAsyncCalculator.class.getCanonicalName());
    private        ExecutorService executor;
    private        String          calcId;

    public MultiThreadedAsyncCalculator(String calcId) {
        this.calcId = calcId;
        executor = Executors.newScheduledThreadPool(1);
//         executor = Executors.newSingleThreadExecutor();
//         executor = Executors.newFixedThreadPool(2);
//        executor = Executors.newCachedThreadPool();
        logger.info("Using Executor : " + executor.getClass().getCanonicalName());
    }

    private static Integer checkLoop(Future<Integer> future) throws InterruptedException, ExecutionException {
        while (!future.isDone()) {
            logger.info("Calculating...");
            Thread.sleep(200);
        }
        return future.get();
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

    public void shutdown() {
        executor.shutdown();
    }

    public Future<Integer> square(Integer input) {
        // lambda equivalent of represent squareAsCallable function below
        return executor.submit(() -> getSquare(input));
    }

    public Future<Integer> squareAsCallable(Integer input) {
        return executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return MultiThreadedAsyncCalculator.this.getSquare(input);
            }
        });
    }

    private Integer getSquare(Integer input) throws InterruptedException {
        Thread.sleep(1000L);
        Integer result = input * input;
        logger.info(result);
        return result;
    }
}
