package com.javarush.task.task29.task2905;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/* 
Странные ошибки О_о
*/
public class Solution {
    private final static int NUMBER_OF_THREADS = 3; // 3 треда будет обрабатывать нашу очередь
    private final static int MAX_BATCH_SIZE = 100; // Будем вытаскивать по 100 сообщений

    private final Logger logger = Logger.getLogger(Solution.class.getName());
    // Имитация Брокера
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    // Имитация БД
    private final BlockingQueue<String> fakeDatabase = new LinkedBlockingQueue<>();

    public void startCreatingMessages() {
        new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                AtomicInteger j = new AtomicInteger(i);
                messageQueue.add(String.valueOf(j.decrementAndGet()));
            }
        }).start();
    }

    public void startPersistingMessages() {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            var task = new Task();
            task.setDaemon(true);
            task.start();
        }
    }

    private void printResults() {
        System.out.println();
        System.out.println("messageQueue size is " + messageQueue.size());
        System.out.println("fakeDatabase size is " + fakeDatabase.size());
    }

    public static void main(String[] args) throws InterruptedException {
        // Статики во многих местах неуместны, поэтому помещаем все данные в поля класса,
        // затем создаем объект и вызываем его метод
        Solution solution = new Solution();

        solution.startCreatingMessages();
        solution.startPersistingMessages();

        for (int i = 1; i < 15; i++) {
            Thread.sleep(100L * i);
            solution.printResults();
        }
        Thread.sleep(500);
        solution.printResults();
    }

    class Task extends Thread {
        private final Collection<String> batch = new ArrayList<>(MAX_BATCH_SIZE);

        @Override
        public void run() {
            while (true) {
                try {
                    messageQueue.drainTo(batch, MAX_BATCH_SIZE);
                    persistData(batch);
                    batch.clear();
                    Thread.sleep(1);
                } catch (Throwable e) {
                    logger.log(Level.SEVERE, "impossible to persist a batch", e);
                }
            }
        }

        private void persistData(Collection<String> batch) {
            // Представим, что тут мы коннектимся к базе данных, и сохраняем данные в нее
            // Сохранение данных по 1 записи тратит много ресурсов, поэтому делают батчем (группой по несколько)
            fakeDatabase.addAll(batch);
        }
    }
}
