package com.javarush.task.task27.task2705;

/* 
Второй вариант deadlock
*/
public class Solution {
    private final Object lock = new Object();

    public synchronized void firstMethod() {
        synchronized (lock) {
            doSomething();
        }
    }

    public void secondMethod() {
        synchronized (lock) {
            synchronized (this) {
                doSomething();
            }
        }
    }

    private void doSomething() {
        System.out.println("Some important work: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        final Solution ob = new Solution();

        for (int i = 0; i < 10; i++) {
            new Thread(ob::firstMethod).start();
            new Thread(ob::secondMethod).start();
        }
    }
}