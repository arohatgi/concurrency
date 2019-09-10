package com.ar.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    private static Logger logger = LogManager.getLogger(CompletableFutureExample.class.getCanonicalName());
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        logger.info("Starting completable future ");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(CompletableFutureExample::resultString);
        completableFuture.thenAcceptAsync((result) -> {
            logger.info("Future result : " + result);
        });
        logger.info("Future result : " + completableFuture.get());
    }

    private static String resultString() {
        try {
            logger.info("Wait 2 sec...");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Some Data";
    }
}
