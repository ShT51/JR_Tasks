package com.javarush.task.task39.task3913;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Parser {
    private final Pattern pattern;

    public Parser(Pattern pattern) {
        this.pattern = pattern;
    }

    public <T> Stream<String> filtredStream(Stream<String> stream, T param, String groupName) {
        return stream.map(line -> filterWithParam(line, param, groupName)).
                filter(line -> !line.isEmpty());
    }

    public <T> Stream<String> ipStream(Stream<String> stream) {
        return stream.map(line -> mainFilter(line, "ip")).
                filter(line -> !line.isEmpty());
    }

    public <T> Stream<String> nameStream(Stream<String> stream) {
        return stream.map(line -> mainFilter(line, "user")).
                filter(line -> !line.isEmpty());
    }

    public <T> Stream<Date> dateStream(Stream<String> stream) {
        return stream.map(line -> mainFilter(line, "date")).
                filter(line -> !line.isEmpty()).
                map(Converters::dateConverter);
    }

    public <T> Stream<Event> eventStream(Stream<String> stream) {
        return stream.map(line -> mainFilter(line, "event")).
                filter(line -> !line.isEmpty()).
                map(Converters::eventConverter);
    }

    public <T> Stream<Status> statusStream(Stream<String> stream) {
        return stream.map(line -> mainFilter(line, "status")).
                filter(line -> !line.isEmpty()).
                map(Converters::statusConverter);
    }

    public <T> Stream<String> taskStream(Stream<String> stream) {
        return stream.map(line -> mainFilter(line, "task")).
                filter(Objects::nonNull);
    }

    public <T> Stream<String> genericStream(Stream<String> stream, String groupName) {
        return stream.map(line -> mainFilter(line, groupName)).
                filter(Objects::nonNull);
    }

    public  <T> String filterWithParam(String line, T param, String groupName) {
        String result = "";
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String name = matcher.group(groupName);
            if (name == null) {
                continue;
            }
            if (param == null) {
                result = name;
            } else if (name.equals(param.toString())) {
                result = line;
            }
        }
        return result;
    }

    private String mainFilter(String line, String group) {
        Matcher matcher = pattern.matcher(line);
        String result = "";
        while (matcher.find()) {
            result = matcher.group(group);
        }
        return result;
    }
}
