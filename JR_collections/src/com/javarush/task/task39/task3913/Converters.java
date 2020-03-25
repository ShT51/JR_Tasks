package com.javarush.task.task39.task3913;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {

    public static Status statusConverter(String line) {
        Status result = null;
        for (Status status : Status.values()) {
            if (status.name().equals(line.trim())) {
                result = status;
            }
        }
        return result;
    }

    public static Event eventConverter(String line) {
        Event result = null;
        for (Event event : Event.values()) {
            if (event.name().equals(line.trim())) {
                result = event;
            }
        }
        return result;
    }

    public static Date dateConverter(String line) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date logDate = null;
        try {
            if (line == null) {
                logDate = dateFormat.parse("0.00.0000 00:00:00");
            }
            logDate = dateFormat.parse(line);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return logDate;
    }
}
