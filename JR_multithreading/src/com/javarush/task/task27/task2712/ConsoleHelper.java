package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new LinkedList<>();

        writeMessage(Dish.allDishesToString());
        writeMessage("Введите желаемое блюдо: ");
        String dishName;

        while (!(dishName = ConsoleHelper.readString()).equals("exit")) {
            boolean isDishChosen = false;

            if (dishName.isEmpty()) {
                ConsoleHelper.writeMessage("Блюдо не выбрано.");
                continue;
            }
            for (Dish dish : Dish.values()) {
                if (dish.name().equalsIgnoreCase(dishName.trim())) {
                    dishes.add(dish);
                    isDishChosen = true;
                }
            }
            if (!isDishChosen) {
                ConsoleHelper.writeMessage("Такого блюда в меню нет");
            }
        }
        return dishes;
    }
}
