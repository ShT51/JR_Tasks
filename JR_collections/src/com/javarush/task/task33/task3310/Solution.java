package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
        testStrategy(new FileStorageStrategy(), 100);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        return strings.stream().
                map(shortener::getId).
                collect(Collectors.toSet());
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        return keys.stream().
                map(shortener::getString).
                collect(Collectors.toSet());
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());

        Shortener shortener = new Shortener(strategy);

        Set<String> originalStrings = new HashSet<>();
        Set<String> generatedStrings;

        for (int i = 0; i < elementsNumber; i++) {
            String str = Helper.generateRandomString();
            originalStrings.add(str);
        }

        Date startGetId = new Date();
        Set<Long> keys = getIds(shortener, originalStrings);
        Helper.printMessage("Time for getIds = " + (new Date().getTime() - startGetId.getTime()));

        Date startGetString = new Date();
        generatedStrings = getStrings(shortener, keys);
        Helper.printMessage("Time for getStrings = " + (new Date().getTime() - startGetString.getTime()));

        String testResult = (originalStrings.containsAll(generatedStrings)) ? "Тест пройден." : "Тест не пройден.";
        Helper.printMessage(testResult);

    }
}