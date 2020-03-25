package com.javarush.task.task36.task3605;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        TreeSet<Character> treeSet = new TreeSet<>();
        String str;
        while ((str = reader.readLine()) != null) {
            char[] array = str.replaceAll("[\\W+\\d+_]", "").toLowerCase().toCharArray();
            for (Character c : array) {
                treeSet.add(c);
            }
        }
        treeSet.stream().limit(5).forEach(System.out::print);
    }

}
