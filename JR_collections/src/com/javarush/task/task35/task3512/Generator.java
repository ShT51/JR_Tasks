package com.javarush.task.task35.task3512;

public class Generator<T> {

    private Class<T> clazz;

    public Generator(Class<T> clazz) {
        this.clazz = clazz;
    }

    T newInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException rex) {
            System.out.println("exception in newInstance method");
            return null;
        }
    }
}