package com.javarush.task.task39.task3913;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogProducer {
    private Path logDir;
    private Pattern pattern;
    private String groupName;

    public LogProducer(Path logDir, Pattern pattern) {
        this.logDir = logDir;
        this.pattern = pattern;
        this.groupName = "date";
    }

    public Stream<String> linesStream(Date after, Date before) {
        Stream<String> stream = Stream.empty();
        try {
            stream = Files.list(logDir).
                    filter(f -> f.toString().endsWith(".log")).
                    flatMap(LogProducer::readAll).
                    filter(line -> dateFilter(line, after, before));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }

    private static Stream<String> readAll(Path path) {
        Stream<String> stream = Stream.empty();
        try {
            stream = Files.readAllLines(path).stream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }


    private boolean dateFilter(String line, Date after, Date before) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date logDate;
        boolean result = false;

        Matcher matcher = pattern.matcher(line);
        matcher.find();
        try {
            logDate = dateFormat.parse(matcher.group(groupName));
            if ((after == null || logDate.after(after))
                    && (before == null || logDate.before(before))) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
