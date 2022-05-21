package com.javarush.task.task26.task2611;

import java.util.Map;

public class Consumer implements Runnable {
    private final Map<String, String> map;

    public Consumer(Map<String, String> map) {
        this.map = map;
    }

    public void run() {
        Thread currentThread = Thread.currentThread();
        while (!currentThread.isInterrupted()) {
            if (!map.isEmpty()) {
                for (String key : map.keySet()) {
                    System.out.println(map.remove(key));
                }
            }
        }
    }
}