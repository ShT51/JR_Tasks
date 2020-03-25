package com.javarush.task.task29.task2908;

public class Square implements Computable<Integer, Integer> {
    // метод compute класса square просто вычисляет квадрат по стороне
    @Override
    public Integer compute(Integer integer) throws InterruptedException {
        int val = integer.intValue();
        return val * val;
    }
}
