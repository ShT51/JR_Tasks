package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.concurrent.LinkedBlockingQueue;

public class Cook implements Runnable {
    private String name;
    private LinkedBlockingQueue<Order> queue;
    private LinkedBlockingQueue<Order> doneOrders;

    public Cook(String name) {
        this.name = name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void setDoneOrders(LinkedBlockingQueue<Order> doneOrders) {
        this.doneOrders = doneOrders;
    }

    public void startCookingOrder(Order order) throws InterruptedException {
        int cookingTime = order.getTotalCookingTime();
        order.setCookedBy(this);
        Thread.sleep(cookingTime * 10);

        ConsoleHelper.writeMessage(String.format("====\nStart cooking by [%s] - %s, cooking time %dmin, order # %d",
                this, order, cookingTime, order.getOrderID()));

        CookedOrderEventDataRow cookEvent = new CookedOrderEventDataRow(order.getTablet().toString(),
                name, cookingTime * 60, order.getDishes());

        StatisticManager manager = StatisticManager.getInstance();
        manager.register(cookEvent);
    }

    @Override
    public void run() {
        boolean isInterrupted = false;
        Order order;

        while (!isInterrupted) {
            try {
                if ((order = queue.poll()) != null) {
                    this.startCookingOrder(order);
                    doneOrders.add(order);
                    isInterrupted = Thread.interrupted();

                } else {
                    Thread.sleep(10);
                }
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}