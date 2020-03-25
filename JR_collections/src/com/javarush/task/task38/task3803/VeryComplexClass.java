package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.text.SimpleDateFormat;
import java.util.*;

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        List<Object> set = new ArrayList<>();
        set.add("one");

        int one = (int) set.get(0);

    }

    public void methodThrowsNullPointerException() {
        Object a = null;
        Math.abs((int)a);
    }

    public static void main(String[] args) {
        VeryComplexClass obj = new VeryComplexClass();
        obj.methodThrowsNullPointerException();
    }
}
