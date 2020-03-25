package com.javarush.task.task34.task3401;

import java.util.Date;

/*
Числа Фибоначчи с помощью рекурсии
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();

        /*long start = System.currentTimeMillis();
        System.out.println(solution.fibonacci(45));
        System.out.println("Time: " + (System.currentTimeMillis() - start));*/

        long start = System.currentTimeMillis();
        System.out.println(solution.fibonachi(1000));
        System.out.println((System.currentTimeMillis() - start));
    }

    public int fibonacci(int n) {

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public long fibonachi(int n) {
        long[] array = new long[n + 1];
        array[0] = 0;
        array[1] = 1;

        for (int i = 2; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n];
    }
}
