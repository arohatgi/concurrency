package com.ar.concurrency;

import com.ar.concurrency.Tasks.FactorialCalc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolDriver {
    private static Logger logger = LogManager.getLogger(ForkJoinPoolDriver.class.getCanonicalName());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool  forkJoinPool = new ForkJoinPool();
        FactorialCalc calculator   = new FactorialCalc(10);
        forkJoinPool.execute(calculator);
        logger.info("Factorial value : " + calculator.get());
    }
}
