package com.javarush.task.task38.task3804;

public class MessageHandler {

    public static <T> String getMessage(T param) {
        String message = param.toString().replaceAll("_", " ").toLowerCase();
        message = message.substring(0, 1).toUpperCase() + message.substring(1);
        return message;
    }
}
