package com.ar.concurrency;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MaxHeapWithPQ {

    public static void main(String args[]) {
        // create priority queue
        PriorityBlockingQueue<Integer> prq = new PriorityBlockingQueue<Integer>(3, (x,y) -> y-x);
//        PriorityQueue<Integer> prq = new PriorityQueue<>();

        // insert values in the queue
        prq.add(6);
        prq.add(9);
        prq.add(5);
        prq.add(64);
        prq.add(6);

        //print values
        while (!prq.isEmpty()) {
            System.out.print(prq.poll()+" ");
        }
    }

}

//        PriorityQueue<Integer> prq = new PriorityQueue<>((x,y) -> y-x);
