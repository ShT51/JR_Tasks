package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

public class Solution {

    public static Class getFactoryClass() {
        return ExceptionFabric.class;
    }

    public static void main(String[] args) {
        System.out.println(ExceptionFabric.getException(DatabaseExceptionMessage.NO_RESULT_DUE_TO_TIMEOUT));
    }
}