package com.javarush.task.task37.task3702;

import java.net.HttpURLConnection;

public interface AbstractFactory {
    public Human getPerson(int age);
}
