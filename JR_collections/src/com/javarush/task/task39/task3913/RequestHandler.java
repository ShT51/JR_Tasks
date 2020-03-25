package com.javarush.task.task39.task3913;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    static Map<String, String> tags = new HashMap<>();
    //static private final Pattern pattern = Pattern.compile("get\\s+(?<field1>[\\w]+)(\\s*for\\s*(?<field2>[\\w]+)\\s=\\s\"(?<value1>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+|.+)\")?");
    static private final Pattern pattern = Pattern.compile("get\\s(?<field1>\\w+)(\\sfor\\s(?<field2>\\w+)\\s=\\s\"(?<value1>.*?)\")?(\\sand\\sdate\\sbetween\\s\"(?<after>.*)\"\\sand\\s\"(?<before>.*)\")?");
    private String field1;
    private String field2;
    private String value1;
    private String after_str;
    private String before_str;
    private Date after;
    private Date before;

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getValue1() {
        return value1;
    }

    public Date getAfter() {
        return after;
    }

    public Date getBefore() {
        return before;
    }

    public void getTags(String query) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()) {
            field1 = matcher.group("field1");
            field2 = matcher.group("field2");
            value1 = matcher.group("value1");
            after_str = matcher.group("after");
            before_str = matcher.group("before");

            if (after_str != null && before_str != null) {
                try {
                    after = dateFormat.parse(after_str);
                    before = dateFormat.parse(before_str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
