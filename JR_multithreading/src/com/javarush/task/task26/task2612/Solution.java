package com.javarush.task.task26.task2612;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 
Весь мир играет комедию
*/
public class Solution {
    private final Lock lock = new ReentrantLock();

    public void someMethod() {
        // Implement the logic here. Use the lock field
        if (lock.tryLock()) {
            try {
                actionIfLockIsFree();
            } finally {
                lock.unlock();
            }
        } else {
            actionIfLockIsBusy();
        }
    }

    public void actionIfLockIsFree() {
        lock.lock();
        System.out.println("action if lock is Free");
    }

    public void actionIfLockIsBusy() {
        System.out.println("action if lock is Busy");
    }
}
