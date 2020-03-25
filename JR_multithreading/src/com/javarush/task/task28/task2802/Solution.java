package com.javarush.task.task28.task2802;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* 
Пишем свою ThreadFactory
*/
public class Solution {

    public static void main(String[] args) {
        class EmulatorThreadFactoryTask implements Runnable {
            @Override
            public void run() {
                emulateThreadFactory();
            }
        }

        ThreadGroup group = new ThreadGroup("firstGroup");
        Thread thread = new Thread(group, new EmulatorThreadFactoryTask());

        ThreadGroup group2 = new ThreadGroup("secondGroup");
        Thread thread2 = new Thread(group2, new EmulatorThreadFactoryTask());

        thread.start();
        thread2.start();
    }

    private static void emulateThreadFactory() {
        AmigoThreadFactory factory = new AmigoThreadFactory();
        Runnable r = () -> System.out.println(Thread.currentThread().getName());
        factory.newThread(r).start();
        factory.newThread(r).start();
        factory.newThread(r).start();
    }

    public static class AmigoThreadFactory implements ThreadFactory {
        private static AtomicInteger factoryClass = new AtomicInteger(0);
        private AtomicInteger factoryObject = new AtomicInteger(0);

        public AmigoThreadFactory() {
            factoryClass.incrementAndGet();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread newThread = new Thread(r);
            newThread.setDaemon(false);
            newThread.setPriority(Thread.NORM_PRIORITY);

            String threadName = String.format("%s-pool-%s-thread-%s",
                    newThread.getThreadGroup().getName(),
                    factoryClass.get(),
                    factoryObject.incrementAndGet());

            newThread.setName(threadName);
            return newThread;
        }
    }
}
