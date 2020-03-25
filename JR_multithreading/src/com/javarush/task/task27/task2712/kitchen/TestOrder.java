package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        ConsoleHelper.writeMessage(
                "==== new order ====\n"
                + Dish.allDishesToString()
                + "\n==== order was made ====");

        // список всех блюд
        List<Dish> allDishes = new ArrayList<>(Arrays.asList(Dish.values()));
        // конечный список заказанных блюд
        dishes = new ArrayList<>();
        // рандомное кол-во блюд в заказе
        int dishQTY = ThreadLocalRandom.current().nextInt(1, 6);

        for (int i = 0; i < dishQTY; i++) {
            // рандомное блюдо в заказе
            int dishNumber = ThreadLocalRandom.current().nextInt(0, Dish.values().length);
            dishes.add(allDishes.get(dishNumber));
        }
    }
}
