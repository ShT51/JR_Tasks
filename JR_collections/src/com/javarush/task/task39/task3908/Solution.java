package com.javarush.task.task39.task3908;

import java.util.ArrayList;
import java.util.HashMap;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {
    }

    public static boolean isPalindromePermutation(String s) {
        char[] array = s.toLowerCase().toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        int count = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] == array[j]) {
                    count++;
                }
            }
            if (!map.containsKey(array[i])) {
                map.put(array[i], count);
            }
            count = 0;
        }

        map.forEach((k, v) -> list.add(v));

        for (Integer integer : list) {
            if (integer % 2 == 0) {
                count++;
            }
        }
        return count == list.size() || (list.size() - count) == 1;
    }
}
