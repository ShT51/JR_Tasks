package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date start = new Date();
        ids.addAll(strings.stream().map(shortener::getId).collect(Collectors.toSet()));
        return (new Date()).getTime() - start.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date start = new Date();
        strings.addAll(ids.stream().map(shortener::getString).collect(Collectors.toSet()));
        return (new Date()).getTime() - start.getTime();
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();
        Set<String> strings = new HashSet<>();

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        long genId1 = getTimeToGetIds(shortener1, origStrings, ids1);
        long genId2 = getTimeToGetIds(shortener2, origStrings, ids2);
        Assert.assertTrue(genId1 > genId2);

        long genStr1 = getTimeToGetStrings(shortener1, ids1, strings);
        long genStr2 = getTimeToGetStrings(shortener2, ids2, strings);
        Assert.assertEquals(genStr1, genStr2, 30);
    }
}
