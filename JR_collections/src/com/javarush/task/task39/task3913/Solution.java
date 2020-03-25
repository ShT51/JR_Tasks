package com.javarush.task.task39.task3913;

import org.w3c.dom.ls.LSOutput;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Solution {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date before = dateFormat.parse("01.01.2029 00:00:00");
        Date after = dateFormat.parse("01.01.2021 00:00:00");

        LogParser logParser = new LogParser(Paths.get("D:\\Yandex Disk\\YandexDisk\\Coding\\Compilation\\Java\\JavaRush\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));
/*
        System.out.println("# getUniqueIPs + count #");
        Set<String> result = logParser.getUniqueIPs(null, null);
        result.forEach(System.out::println);
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));

        System.out.println("# getIPsForUser Amigo #");
        result = logParser.getIPsForUser("Amigo", null, null);
        result.forEach(System.out::println);

        System.out.print("getIPsForEvent DONE_TASK");
        result = logParser.getIPsForEvent(Event.DONE_TASK, null, null);
        result.forEach(System.out::println);

        System.out.print("getIPsForStatus ERROR");
        result = logParser.getIPsForStatus(Status.ERROR, null, null);
        result.forEach(System.out::println);

        Set<String> users;

        System.out.println("# getAllUsers + number");
        users = logParser.getAllUsers();
        users.forEach(System.out::println);
        System.out.println(logParser.getNumberOfUsers(null, null));

        System.out.println("# getNumberOfUserEvents + Amigo");
        System.out.println(logParser.getNumberOfUserEvents("Amigo", null, null));

        System.out.println("# getUsersForIP + 127.0.0.1");
        users = logParser.getUsersForIP("127.0.0.1", null, null);
        users.forEach(System.out::println);

        System.out.println("# getLoggedUsers + LOGIN");
        users = logParser.getLoggedUsers(null, null);
        users.forEach(System.out::println);

        System.out.println("# getDownloadedPluginUsers #");
        users = logParser.getDownloadedPluginUsers(null, null);
        users.forEach(System.out::println);

        System.out.println("# getWroteMessageUsers #");
        users = logParser.getWroteMessageUsers(null, null);
        users.forEach(System.out::println);

        System.out.println("# getSolvedTaskUsers #");
        users = logParser.getSolvedTaskUsers(null, null);
        users.forEach(System.out::println);

        System.out.println("# getSolvedTaskUsers 18#");
        users = logParser.getSolvedTaskUsers(null, null, 18);
        users.forEach(System.out::println);

        System.out.println("# getDoneTaskUsers #");
        users = logParser.getDoneTaskUsers(null, null);
        users.forEach(System.out::println);

        System.out.println("# getDoneTaskUsers 18#");
        users = logParser.getDoneTaskUsers(null, null, 18);
        users.forEach(System.out::println);


        Set<Date> dates;

        System.out.println("# getDatesForUserAndEvent Eduard Petrovich Morozko + WRITE_MESSAGE #");
        dates = logParser.getDatesForUserAndEvent("Eduard Petrovich Morozko", Event.WRITE_MESSAGE, null, null);
        dates.forEach(System.out::println);

        System.out.println("# getDatesWhenSomethingFailed #");
        dates = logParser.getDatesWhenSomethingFailed( null, null);
        dates.forEach(System.out::println);

        System.out.println("# getDatesWhenErrorHappened #");
        dates = logParser.getDatesWhenErrorHappened( null, null);
        dates.forEach(System.out::println);

        System.out.println("# getDateWhenUserLoggedFirstTime #");
        Date date = logParser.getDateWhenUserLoggedFirstTime("Amigo", null, null);
        System.out.println(date);

        System.out.println("# getDateWhenUserSolvedTask 18#");
        date = logParser.getDateWhenUserSolvedTask("Amigo", 18, null, null);
        System.out.println(date);

        System.out.println("# getDateWhenUserDoneTask 15#");
        date = logParser.getDateWhenUserDoneTask("Vasya Pupkin", 15, null, null);
        System.out.println(date);

        System.out.println("# getDatesWhenUserWroteMessage Eduard Petrovich Morozko #");
        dates = logParser.getDatesWhenUserWroteMessage("Eduard Petrovich Morozko", null, null);
        dates.forEach(System.out::println);

        System.out.println("#  getDatesWhenUserDownloadedPlugin #");
        dates = logParser.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null);
        dates.forEach(System.out::println);


        Set<Event> events;

        System.out.println("# getAllEvents  + qty#");
        events = logParser.getAllEvents(null, null);
        events.forEach(System.out::println);
        System.out.println(logParser.getNumberOfAllEvents(null, null));

        System.out.println("# getEventsForIP#");
        events = logParser.getEventsForIP("192.168.100.2", null, null);
        events.forEach(System.out::println);

        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(18, null, null));

        Map<Integer, Integer> map = logParser.getAllSolvedTasksAndTheirNumber(null, null);
        map.forEach((k,v)-> System.out.println(k + " = " + v));

        System.out.println();

        Map<Integer, Integer> map2 = logParser.getAllDoneTasksAndTheirNumber(null, null);
        map2.forEach((k,v)-> System.out.println(k + " = " + v));

 */

        Set<Object> set = logParser.execute("get ip");
        set.forEach(System.out::println);
    }
}