package com.javarush.task.task29.task2913;

import java.util.Random;
import java.util.StringJoiner;

/* 
Замена рекурсии
*/

public class Solution {
    private static int numberA;
    private static int numberB;

    public static String getAllNumbersBetween(int a, int b) {
        StringJoiner sj = new StringJoiner(" ");
        if (a < b) {
            for (int i = a; i <= b; i++) {
                sj.add(String.valueOf(i));
            }
        } else if (a > b) {
            for (int i = a; i >= b; i--) {
                sj.add(String.valueOf(i));
            }
        }
        return sj.toString();
    }

    public static void main(String[] args) {
        Random random = new Random();
        numberA = random.nextInt(1000);
        numberB = random.nextInt(1000);
        System.out.println(getAllNumbersBetween(numberA, numberB));
        System.out.println(getAllNumbersBetween(numberB, numberA));
    }
}