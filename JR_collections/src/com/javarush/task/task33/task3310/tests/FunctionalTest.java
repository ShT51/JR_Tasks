package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String str1 = Helper.generateRandomString();
        String str2 = Helper.generateRandomString();
        String str3 = str1;

        Long id1 = shortener.getId(str1);
        Long id2 = shortener.getId(str2);
        Long id3 = shortener.getId(str3);

        Assert.assertNotEquals(id1, id2);
        Assert.assertNotEquals(id3, id2);

        Assert.assertEquals(id1, id3);

        String genStr1 = shortener.getString(id1);
        String genStr2 = shortener.getString(id2);
        String genStr3 = shortener.getString(id3);

        Assert.assertEquals(str1, genStr1);
        Assert.assertEquals(str2, genStr2);
        Assert.assertEquals(str3, genStr3);
    }

    @Test
    public void testHashMapStorageStrategy() {
        HashMapStorageStrategy hashMap = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(hashMap);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        OurHashMapStorageStrategy ourHashMap = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashMap);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        FileStorageStrategy fileSSt = new FileStorageStrategy();
        Shortener shortener = new Shortener(fileSSt);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        HashBiMapStorageStrategy hashBiMap = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(hashBiMap);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        DualHashBidiMapStorageStrategy dualHashMap = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(dualHashMap);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        OurHashBiMapStorageStrategy ourHashBiMap = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashBiMap);
        testStorage(shortener);
    }
}
