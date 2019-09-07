package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MultiThreadedAsynDriver {
    private static Logger          logger = LogManager.getLogger(MultiThreadedAsynDriver.class.getCanonicalName());
    private        MultiThreadedAsyncCalculator asyncCalculator;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        MultiThreadedAsyncCalculator asyncCalcOne = new MultiThreadedAsyncCalculator("asyncOne");
        Future<Integer>              future1      = asyncCalcOne.square(10);
        Future<Integer>              future2      = asyncCalcOne.square(20);

        while (!(future1.isDone() && future2.isDone())) {
            logger.info(String.format("future1 is %s and future2 is %s", future1.isDone() ? "done" : "not done", future2.isDone() ? "done" : "not done"));
            Thread.sleep(300);
        }
        Integer result1 = future1.get();
        Integer result2 = future2.get();
        logger.info(result1 + " and " + result2);
        asyncCalcOne.shutdown();
    }
}
