package com.ar.concurrency.Tasks;

import java.util.concurrent.RecursiveTask;

public class FactorialCalc extends RecursiveTask<Integer> {
    private Integer num;

    public FactorialCalc(Integer n) {
        this.n = num;
    }

    @Override
    protected Integer compute() {
        if (num <= 1) {
            return num;
        }
        FactorialCalc calculator = new FactorialCalc(n - 1);
        calculator.fork();
        return num * num + calculator.join();
    }
}
