package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int count = 0;
        for (List<V> lists : map.values()) {
            count += lists.size();
        }
        return count;
    }

    @Override
    public V put(K key, V value) {
        List<V> list;
        V result = null;

        if (!map.containsKey(key)) {
            list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        } else {
            list = map.get(key);
            result = list.get(list.size() - 1);
            if (list.size() >= repeatCount) {
                list.remove(0);
            }
            list.add(value);
        }
        return result;
    }

    @Override
    public V remove(Object key) {
        List<V> list;
        V result = null;
        if (map.containsKey(key)) {
            list = map.get(key);
            result = list.get(0);
            list.remove(0);
            if (list.size() == 0) {
                map.remove(key);
            }
        }
        return result;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = new ArrayList<>();
        for (List<V> list : map.values()) {
            collection.addAll(list);
        }
        return collection;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        boolean result = false;
        for (List<V> list : map.values()) {
            if (list.contains(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}