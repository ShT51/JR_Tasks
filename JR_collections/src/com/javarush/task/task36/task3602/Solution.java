package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class[] classes = Collections.class.getDeclaredClasses();
        int arg = 0;
        Class result = null;

        for (Class clazz : classes) {
            if (checkModifications(clazz) && checkInterfaces(clazz, "List")) {
                try {
                    checkMethod(clazz, arg);
                } catch (InvocationTargetException e) {
                    if (e.getCause().toString().equals("java.lang.IndexOutOfBoundsException: Index: " + arg)) {
                        result = clazz;
                    }
                }
            }
        }
        return result;
    }

    public static boolean checkInterfaces(Class clazz, String intName) {
        Class[] intfList = clazz.getInterfaces();
        if (intfList.length == 0) {
            return false;
        }
        for (Class intf : intfList) {
            if (intf.getSimpleName().equals(intName)) {
                return true;
            }
        }
        return (checkInterfaces(clazz.getSuperclass(), intName));
    }

    public static boolean checkModifications(Class clazz) {
        int mod = clazz.getModifiers();
        return (Modifier.isPrivate(mod) && Modifier.isStatic(mod));
    }

    public static void checkMethod(Class clazz, int arg) throws InvocationTargetException {
        Method method;
        Constructor constructor;
        try {
            method = clazz.getDeclaredMethod("get", int.class);
            method.setAccessible(true);
            constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);

            try {
                method.invoke(constructor.newInstance(), arg);
            } catch (IllegalAccessException | InstantiationException e) {}

        } catch (NoSuchMethodException e) {
            System.out.println(clazz.getSimpleName() + " don't have such method/constructor");
        }
    }
}