package com.javarush.task.task32.task3203;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/*
Пишем стек-трейс
*/
public class Solution {
    public static void main(String[] args) {
        String text = getStackTrace(new IndexOutOfBoundsException("fff"));
        System.out.println(text);
    }

    public static String getStackTrace(Throwable throwable) {
        Writer strWrt = new StringWriter();
        PrintWriter prWrt = new PrintWriter(strWrt);

        throwable.printStackTrace(prWrt);
        return strWrt.toString();
    }
}