package com.ar.concurrency.Tasks;

import java.util.concurrent.RecursiveTask;

public class FactorialCalc extends RecursiveTask<Integer> {
    private Integer n;

    public FactorialCalc(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        FactorialCalc calculator = new FactorialCalc(n - 1);
        calculator.fork();
        return n * n + calculator.join();
    }
}