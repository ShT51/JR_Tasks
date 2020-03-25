package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable {
     Long key;
     String value;
     Entry next;
     int hash;

    public Entry(int hash, Long key, String value,  Entry next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    public boolean equals(Object o) {
        if (o instanceof Entry) {
            Entry e = (Entry) o;
            return (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue()));
        }
        return false;
    }

    public String toString() {
        return key + "=" + value;
    }
}
