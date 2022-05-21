package com.javarush.task.task27.task2707;

/*
Определяем порядок захвата монитора
*/
public class Solution {
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    public static boolean isLockOrderNormal(final Solution solution, final Object obj1, final Object obj2) throws Exception {
        //do something here
        Thread thread1 = new Thread(() -> {
            synchronized (obj1) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (obj2) {}
        });

        Thread thread2 = new Thread(() -> solution.someMethodWithSynchronizedBlocks(obj1, obj2));

        thread1.start();
        Thread.sleep(100);
        thread2.start();
        Thread.sleep(1000);
        if (thread2.getState().equals(Thread.State.BLOCKED)) return false;
        return true;
    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isLockOrderNormal(solution, o1, o2));
    }
}
