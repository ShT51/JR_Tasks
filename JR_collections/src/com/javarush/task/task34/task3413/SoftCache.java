package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        try {
            SoftReference<AnyObject> softReference = cacheMap.get(key);
            return softReference.get();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public AnyObject put(Long key, AnyObject value) {
        try {
            SoftReference<AnyObject> softRef = cacheMap.get(key);
            AnyObject strongRef = softRef.get();
            softRef.clear();
            return strongRef;
        } catch (NullPointerException ex) {
            cacheMap.put(key, new SoftReference<>(value));
            return null;
        }
    }

    public AnyObject remove(Long key) {
        try {
            SoftReference<AnyObject> softRef = cacheMap.get(key);
            AnyObject strongRef = softRef.get();
            cacheMap.remove(key);
            softRef.clear();
            return strongRef;
        } catch (NullPointerException ex) {
            return null;

        }
    }
}