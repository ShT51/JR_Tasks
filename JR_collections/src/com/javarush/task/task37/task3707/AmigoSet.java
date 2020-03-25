package com.javarush.task.task37.task3707;

import java.io.*;
import java.util.*;

public class AmigoSet<E> extends AbstractSet implements Set, Serializable, Cloneable {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
        addAll(c);
    }

    @Override
    public boolean add(Object e) {
        if (map.containsKey(e)) {
            return false;
        } else {
            map.put((E) e, PRESENT);
            return true;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() {
        try {
            AmigoSet<E> amigoSet = (AmigoSet<E>) super.clone();
            amigoSet.map = (HashMap<E, Object>) map.clone();
            return amigoSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // Write out HashMap capacity and load factor
        float loadFactor = HashMapReflectionHelper.callHiddenMethod(this.map, "loadFactor");
        int capacity = HashMapReflectionHelper.callHiddenMethod(this.map, "capacity");

        oos.writeObject(loadFactor);
        oos.writeObject(capacity);
        // Write out size
        oos.writeObject(map.size());
        // Write out all elements in the proper order.
        for (E e : map.keySet()) {
            oos.writeObject(e);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // Read load factor and verify positive and non NaN.

        float loadFactor = (float) ois.readObject();
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " + loadFactor);
        }

        // Read capacity and verify non-negative.
        int capacity = (int) ois.readObject();
        if (capacity < 0) {
            throw new InvalidObjectException("Illegal capacity: " + capacity);
        }
        // Read size and verify non-negative.
        int size = (int) ois.readObject();
        if (size < 0) {
            throw new InvalidObjectException("Illegal size: " + size);
        }

        HashMap<E, Object> loadedMap = new HashMap<>(capacity, loadFactor);
        for (int i = 0; i < size; i++) {
            loadedMap.put((E) ois.readObject(), PRESENT);
        }
        map = loadedMap;
    }
}
