package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        int result = 0;
        List<String> romeNumbers = Arrays.asList("I", "V", "X", "L", "C", "D", "M", "IV", "IX", "XL", "XC", "CD", "CM");
        List<Integer> arabNumbers = Arrays.asList(1, 5, 10, 50, 100, 500, 1000, 4, 9, 40, 90, 400, 900);
        HashMap<String, Integer> convertedRomeNumbers = fillMapWithNumbers(romeNumbers, arabNumbers);

        Matcher matcher = Pattern.compile("M|CM|D|CD|C|XC|L|XL|X|IX|V|IV|I").matcher(s);

        if (!matcher.find()) {
            throw new IllegalArgumentException("не допустимое значение");
        }

        while (matcher.find()) {
            result += convertedRomeNumbers.get(matcher.group());
        }

        return result;
    }


    public static HashMap<String, Integer> fillMapWithNumbers(List<String> romeNumbers, List<Integer> arabNumbers) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < romeNumbers.size(); i++) {
            map.put(romeNumbers.get(i), arabNumbers.get(i));
        }
        return map;
    }
}
