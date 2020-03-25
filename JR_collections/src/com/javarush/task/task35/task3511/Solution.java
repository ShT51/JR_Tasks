package com.javarush.task.task35.task3511;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* 
Wildcards для коллекций
*/
public class Solution {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        System.out.println(sum(list));
        System.out.println(multiply(list));
        System.out.println(concat(list));
    }

    //суммирует все элементы списка, в котором находятся любые числа
    public static Double sum(List<? extends Number> list) {
        Double result = 0.0;
        for (int i = 0; i < list.size(); i++) {
            Number numb = list.get(i);
            result += numb.doubleValue();
        }
        return result;
    }

    // перемножает между собой все элементы списка, в котором находятся любые числа,
    public static Double multiply(List <? extends Number> list) {
        Double result = 1.0;
        for (int i = 0; i < list.size(); i++) {
            Number numb = list.get(i);
            result *= numb.doubleValue();
        }
        return result;
    }

    // соединяет все элементы списка в одну строку,
    public static String concat(List<?> list) {
        StringBuilder builder = new StringBuilder();
        for (Object obj : list) {
            builder.append(obj);
        }
        return builder.toString();
    }

    // принимает на вход список любых коллекций и возвращает результирующий список, в котором находятся все элементы из списков.
    public static <T> List combine(List<? extends Collection<T>> list) {
        List result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Collection collection = list.get(i);
            result.addAll(collection);
        }
        return result;
    }
}
