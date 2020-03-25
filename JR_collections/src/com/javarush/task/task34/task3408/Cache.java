package com.javarush.task.task34.task3408;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();
    //TODO add your code here

    public V getByKey(K key, Class<V> clazz) {
        //TODO add your code here
        V newObj = cache.get(key);
        if (newObj == null) {
            try {
                newObj = clazz.getConstructor(key.getClass()).newInstance(key);
                cache.put(key, newObj);
            } catch (Exception e) {
                System.out.println("проблемы с reflexion");
            }
        }
        return newObj;
    }
    public boolean put(V obj) {
        //TODO add your code here
        try {
            Method method = obj.getClass().getDeclaredMethod("getKey");
            method.setAccessible(true);
            K key = (K) method.invoke(obj);
            cache.put(key, obj);
            return true;
        } catch (Exception e) {
            System.out.println("Something wrong in put method");
        }
        return false;
    }

    public int size() {
        return cache.size();
    }
}
