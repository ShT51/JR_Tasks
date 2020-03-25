package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static volatile AtomicInteger orderNumber = new AtomicInteger(1);
    private int orderID;
    private final Tablet tablet;
    private Cook cookedBy;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        orderID = orderNumber.getAndIncrement();
        this.tablet = tablet;
        initDishes();
    }

    public int getOrderID() {
        return orderID;
    }

    public void setCookedBy(Cook cookedBy) {
        this.cookedBy = cookedBy;
    }

    public Cook getCookedBy() {
        return cookedBy;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public int getTotalCookingTime() {
        int totalTime = 0;

        for (Dish dish : dishes) {
            totalTime += dish.getDuration();
        }
        return totalTime;
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    protected void initDishes() throws IOException {
        ConsoleHelper.writeMessage(Dish.allDishesToString());
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        return String.format("Your order # %d: %s of %s", orderID, dishes.toString(), tablet.toString());
    }
}
