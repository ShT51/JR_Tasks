package com.javarush.task.task26.task2610;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Object result = queue.take();
                System.out.println(result.toString());
            }
        } catch (InterruptedException e) {
            System.out.printf("[%s] thread was terminated%n", Thread.currentThread().getName());
        }
    }
}
