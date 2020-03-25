package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {

    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Order createOrder() {
        Order newOrder;
        try {
            newOrder = new Order(this);
            insideCreateOrder(newOrder);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return null;
        }
        return newOrder;
    }

    public synchronized void createTestOrder() {
        Order testOrder;
        try {
            testOrder = new TestOrder(this);
            insideCreateOrder(testOrder);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void insideCreateOrder(Order order) throws InterruptedException {
        if (!order.isEmpty()) {
            queue.add(order);
            ConsoleHelper.writeMessage(order.toString());
            try {
                new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
            } catch (NoVideoAvailableException nvex) {
                logger.log(Level.INFO, "No video is available for the order " + order);
            }
        }
    }

    @Override
    public String toString() {
        return "Tablet{number=" + number + "}";
    }
}
