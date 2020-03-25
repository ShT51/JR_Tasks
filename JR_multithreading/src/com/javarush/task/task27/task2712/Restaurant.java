package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> NEW_ORDERS = new LinkedBlockingQueue<>();
    private static final LinkedBlockingQueue<Order> DONE_ORDERS = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        List<Cook> cooks = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Cook cook = new Cook("Chief_" + i);
            cook.setQueue(NEW_ORDERS);
            cook.setDoneOrders(DONE_ORDERS);
            cooks.add(cook);
        }
        List<Waiter> waiters = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Waiter waiter = new Waiter();
            waiter.setOrdersToServe(DONE_ORDERS);
            waiters.add(waiter);
        }

        List<Tablet> tablets = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Tablet tablet = new Tablet(i);
            tablets.add(tablet);
            tablet.setQueue(NEW_ORDERS);
        }

        ExecutorService cookExecutor = Executors.newCachedThreadPool();
        cooks.forEach(cookExecutor::execute);

        ExecutorService waiterExecutor = Executors.newCachedThreadPool();
        waiters.forEach(waiterExecutor::execute);

        Thread generateOrders = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        generateOrders.start();

        try {
            Thread.sleep(500);
            generateOrders.interrupt();
            Thread.sleep(1500);

            while (true) {
                if (NEW_ORDERS.isEmpty()) {
                    cookExecutor.shutdownNow();
                    break;
                }
            }
            Thread.sleep(1500);
            while (true) {
                if (DONE_ORDERS.isEmpty()) {
                    waiterExecutor.shutdownNow();
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DirectorTablet directorTablet = new DirectorTablet();
        System.out.println("====================");
        directorTablet.printAdvertisementProfit();
        System.out.println("====================");
        directorTablet.printCookWorkloading();
        System.out.println("====================");
        directorTablet.printActiveVideoSet();
        System.out.println("====================");
        directorTablet.printArchivedVideoSet();

    }
}
