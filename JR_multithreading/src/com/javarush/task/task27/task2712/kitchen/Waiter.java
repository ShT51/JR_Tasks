package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.concurrent.LinkedBlockingQueue;

public class Waiter implements Runnable {
    private LinkedBlockingQueue<Order> ordersToServe;

    public void setOrdersToServe(LinkedBlockingQueue<Order> ordersToServe) {
        this.ordersToServe = ordersToServe;
    }

    @Override
    public void run() {
        boolean isInterrupted = false;

        while (!isInterrupted) {
            try {
                Order order;
                if ((order = ordersToServe.poll()) != null) {
                    String string = "====\n" +
                            order + " was cooked by " + order.getCookedBy() +
                            "\n" +
                            order + " bring to the Tablet " + order.getTablet();
                    ConsoleHelper.writeMessage(string);
                    isInterrupted = Thread.interrupted();
                } else {
                    Thread.sleep(10);
                }
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}

