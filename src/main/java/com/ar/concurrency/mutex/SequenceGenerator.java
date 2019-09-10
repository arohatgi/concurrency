package com.ar.concurrency.mutex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SequenceGenerator {
    private static Logger                 logger       = LogManager.getLogger(SequenceGenerator.class.getSimpleName());
    private        ReentrantReadWriteLock lock         = new ReentrantReadWriteLock();
    private        int                    currentValue = 0;

    public int getUnSafeNextSequence() {
        currentValue++;
        logger.debug(String.format("Current Value: %d", currentValue));
        return currentValue;
    }

    public synchronized int getNextSequence(String idValue) {
        currentValue++;
        logger.debug(String.format("Id: %s, Value: %d", idValue, currentValue));
        return currentValue;
    }

    public int getLockableNextSequence(String idValue) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            logger.info(String.format("Id: %s, Value: %d", idValue, currentValue));
            currentValue++;
        } finally {
            writeLock.unlock();
        }
        return currentValue;
    }

    enum SequenceTypeEnum {
        UNSAFE, SYNCHRONIZED, LOCKABLE;
    }
}
