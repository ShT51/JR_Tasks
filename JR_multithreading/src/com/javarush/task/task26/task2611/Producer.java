package com.javarush.task.task26.task2611;

import java.util.Map;

public class Producer implements Runnable {

    private final Map<String, String> map;

    public Producer(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10; i++) {
                String key = String.valueOf(i);
                String value = "Some text for " + key;
                map.put(key, value);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.printf("[%s] thread was terminated%n", Thread.currentThread().getName());
        }
    }
}
