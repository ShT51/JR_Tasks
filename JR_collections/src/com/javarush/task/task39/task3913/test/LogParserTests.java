package com.javarush.task.task39.task3913.test;

import com.javarush.task.task39.task3913.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LogParserTests {
    private static final Path DIR = Paths.get("C:\\Users\\pc\\YandexDisk\\Coding\\Compilation\\Java\\JavaRush\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs");
    private final static Pattern PT = Pattern.compile("(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s(?<user>[a-zA-Z ]+)\\s(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s(?<event>[\\w]+)\\s?((?<task>[\\d]+)|)\\s(?<status>[\\w]+)");

    private LogParser logParser = new LogParser(DIR);
    private static List<String> allLines;
    private static Set<String> actual_IpSet = new HashSet<>();
    private static Set<String> actual_UsersSet = new HashSet<>();
    private static Set<Event> actual_EventSet = new HashSet<>();
    private static Set<Status> actual_StatusSet = new HashSet<>();
    private static Set<Date> actual_DateSet = new HashSet<>();

    @Before
    public void clearThe_ActualSets() {
        actual_IpSet.clear();
        actual_UsersSet.clear();
        actual_EventSet.clear();
        actual_StatusSet.clear();
        actual_DateSet.clear();
    }

    @BeforeClass
    public static void setUp() throws IOException {
        allLines = Files.list(DIR).
                flatMap(LogParserTests::readAll).
                collect(Collectors.toList());
        allLines.forEach(System.out::println);
    }


    public static Set<String> getSet(String groupName) {
        return allLines.stream().
                map(line -> mainFilter(line, groupName)).
                collect(Collectors.toSet());
    }

    public static Set<Date> getDateSet() {
        return allLines.stream().map(line -> mainFilter(line, "date")).
                filter(Objects::nonNull).
                map(Converters::dateConverter).
                collect(Collectors.toSet());
    }

    public static Set<Event> getEventSet() {
        return allLines.stream().map(line -> mainFilter(line, "event")).
                filter(Objects::nonNull).
                map(Converters::eventConverter).
                collect(Collectors.toSet());
    }

    public static Set<Status> getStatusSet() {
        return allLines.stream().map(line -> mainFilter(line, "status")).
                filter(Objects::nonNull).
                map(Converters::statusConverter).
                collect(Collectors.toSet());
    }

    public static String mainFilter(String line, String group) {
        Matcher matcher = PT.matcher(line);
        String result = "";

        while (matcher.find()) {
            result = matcher.group(group);
        }
        return result;
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

    private Date dateConverter(String line) {
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

    // ************** TESTS ************************* //

    @Test
    public void getNumberOfUniqueIPs() {
        int expected = logParser.getNumberOfUniqueIPs(null, null);
        int actual_IpSet_size = getSet("ip").size();
        Assert.assertEquals(expected, actual_IpSet_size);
    }

    @Test
    public void getUniqueIPs() {
        Set<String> expected = logParser.getUniqueIPs(null, null);
        actual_IpSet = getSet("ip");
        Assert.assertEquals(expected, actual_IpSet);
    }

    @Test
    public void getIPsForUser_Amigo() {
        Set<String> expected = logParser.getIPsForUser("Amigo", null, null);
        actual_IpSet.add("127.0.0.1");
        actual_IpSet.add("12.12.12.12");
        actual_IpSet.add("120.120.120.122");
        actual_IpSet.add("127.0.11.1");
        Assert.assertEquals(expected, actual_IpSet);
    }

    @Test
    public void getIPsForEvent_DONE_TASK() {
        Set<String> expected = logParser.getIPsForEvent(Event.DONE_TASK, null, null);
        actual_IpSet.add("192.168.100.2");
        actual_IpSet.add("146.34.15.5");
        actual_IpSet.add("127.0.11.1");
        actual_IpSet.add("120.120.120.122");
        Assert.assertEquals(expected, actual_IpSet);
    }

    @Test
    public void getIPsForStatus_FAILED() {
        Set<String> expected = logParser.getIPsForStatus(Status.FAILED, null, null);
        actual_IpSet.add("127.0.0.1");
        actual_IpSet.add("146.34.15.5");
        Assert.assertEquals(expected, actual_IpSet);
    }

    @Test
    public void getAllUsers() {
        Set<String> expected = logParser.getAllUsers();
        actual_UsersSet = getSet("user");
        Assert.assertEquals(expected, actual_UsersSet);
    }

    @Test
    public void getNumberOfUsers() {
        int expected = logParser.getNumberOfUsers(null, null);
        int actual_NumberOf_UsersSet = getSet("user").size();
        Assert.assertEquals(expected, actual_NumberOf_UsersSet);
    }

    @Test
    public void getNumberOfUserEvents() {
    }

    @Test
    public void getUsersForIP() {
    }

    @Test
    public void getLoggedUsers() {
    }

    @Test
    public void getDownloadedPluginUsers() {
    }

    @Test
    public void getWroteMessageUsers() {
    }

    @Test
    public void getSolvedTaskUsers() {
    }

    @Test
    public void testGetSolvedTaskUsers() {
    }

    @Test
    public void getDoneTaskUsers() {
    }

    @Test
    public void testGetDoneTaskUsers() {
    }

    @Test
    public void getDatesForUserAndEvent() {
    }

    @Test
    public void getDatesWhenSomethingFailed() {
    }

    @Test
    public void getDatesWhenErrorHappened() {
    }

    @Test
    public void getDateWhenUserLoggedFirstTime() {
    }

    @Test
    public void getDateWhenUserSolvedTask() {
    }

    @Test
    public void getDateWhenUserDoneTask() {
    }

    @Test
    public void getDatesWhenUserWroteMessage() {
    }

    @Test
    public void getDatesWhenUserDownloadedPlugin() {
    }

    @Test
    public void getNumberOfAllEvents() {
        int expected = logParser.getNumberOfAllEvents(null, null);
        int actual_NumberOf_EventSet = getSet("event").
                stream().map(Converters::eventConverter).
                collect(Collectors.toSet()).size();
        Assert.assertEquals(expected, actual_NumberOf_EventSet);
    }

    @Test
    public void getAllEvents() {
        Set<Event> expected = logParser.getAllEvents(null, null);
        actual_EventSet = getSet("event").
                stream().map(Converters::eventConverter).
                collect(Collectors.toSet());
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void getEventsForIP() {
        Set<Event> expected = logParser.getEventsForIP("192.168.100.2", null, null);
        actual_EventSet.add(Event.DONE_TASK);
        actual_EventSet.add(Event.SOLVE_TASK);
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void getEventsForUser() {
        Set<Event> expected = logParser.getEventsForUser("Amigo", null, null);
        actual_EventSet.add(Event.SOLVE_TASK);
        actual_EventSet.add(Event.LOGIN);
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void getFailedEvents() {
        Set<Event> expected = logParser.getFailedEvents(null, null);
        actual_EventSet.add(Event.WRITE_MESSAGE);
        actual_EventSet.add(Event.DONE_TASK);
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void getErrorEvents() {
        Set<Event> expected = logParser.getErrorEvents(null, null);
        actual_EventSet.add(Event.SOLVE_TASK);
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void getNumberOfAttemptToSolveTask_18() {
        int expected = logParser.getNumberOfAttemptToSolveTask(18, null, null);
        int actual_NumberOf_Attempt = 5;
        Assert.assertEquals(expected, actual_NumberOf_Attempt);
    }

    @Test
    public void getNumberOfSuccessfulAttemptToSolveTask_18() {
        int expected = logParser.getNumberOfSuccessfulAttemptToSolveTask(18, null, null);
        int actual_NumberOf_SolveTask = 1;
        Assert.assertEquals(expected, actual_NumberOf_SolveTask);
    }

    @Test
    public void getAllSolvedTasksAndTheirNumber() {
        Map<Integer, Integer> expected = logParser.getAllSolvedTasksAndTheirNumber(null, null);
        Map<Integer, Integer> actual_map = new HashMap<>();
        actual_map.put(1, 1);
        actual_map.put(18, 5);
        Assert.assertEquals(expected, actual_map);
    }

    @Test
    public void getAllDoneTasksAndTheirNumber() {
    }

    @Test
    public void execute_getUsers() {
        Set<Object> expected = logParser.execute("get user");
        actual_UsersSet = getSet("user");
        Assert.assertEquals(expected, actual_UsersSet);
    }

    @Test
    public void execute_getUsers_DONE_TASK() {
        Set<Object> expected = logParser.execute("get user for event = \"DONE_TASK\"");
        actual_UsersSet.add("Gogol");
        actual_UsersSet.add("Lermontov");
        actual_UsersSet.add("Vasya Pupkin");
        actual_UsersSet.add("Eduard Petrovich Morozko");
        Assert.assertEquals(expected, actual_UsersSet);
    }

    @Test
    public void execute_getIP() {
        Set<Object> expected = logParser.execute("get ip");
        actual_IpSet = getSet("ip");
        Assert.assertEquals(expected, actual_IpSet);
    }

    @Test
    public void execute_getIP_DONE_TASK() {
        Set<Object> expected = logParser.execute("get ip for event = \"DONE_TASK\"");
        actual_UsersSet.add("120.120.120.122");
        actual_UsersSet.add("146.34.15.5");
        actual_UsersSet.add("192.168.100.2");
        actual_UsersSet.add("127.0.11.1");
        Assert.assertEquals(expected, actual_UsersSet);
    }

    @Test
    public void execute_getDate() {
        Set<Object> expected = logParser.execute("get date");
        actual_DateSet = getDateSet();
        Assert.assertEquals(expected, actual_DateSet);
    }

    @Test
    public void execute_getDate_DONE_TASK() {
        Set<Object> expected = logParser.execute("get date for event = \"DONE_TASK\"");
        Set<String> dateInString = new HashSet<>();
        dateInString.add("30.08.2012 16:08:40");
        dateInString.add("05.01.2021 20:22:55");
        dateInString.add("30.08.2012 16:08:13");
        dateInString.add("29.2.2028 5:4:7");
        actual_DateSet = dateInString.stream().
                map(Converters::dateConverter).
                collect(Collectors.toSet());
        Assert.assertEquals(expected, actual_DateSet);
    }

    @Test
    public void execute_getDate_SOLVE_TASK_After2013_Before2014() {
        Set<Object> expected = logParser.execute("get date for event = \"SOLVE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2022 23:59:59\"");
        Set<String> dateInString = new HashSet<>();
        dateInString.add("19.03.2016 00:00:00");
        dateInString.add("21.10.2021 19:45:25");
        dateInString.add("30.01.2014 12:56:22");
        actual_DateSet = dateInString.stream().
                map(Converters::dateConverter).
                collect(Collectors.toSet());
        Assert.assertEquals(expected, actual_DateSet);
    }


    @Test
    public void execute_getEvent() {
        Set<Object> expected = logParser.execute("get event");
        actual_EventSet = getEventSet();
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void execute_getEvent_DONE_TASK() {
        Set<Object> expected = logParser.execute("get event for user = \"Amigo\"");
        Set<String> eventInString = new HashSet<>();
        eventInString.add("LOGIN");
        eventInString.add("SOLVE_TASK");
        actual_EventSet = eventInString.stream().
                map(Converters::eventConverter).
                collect(Collectors.toSet());
        Assert.assertEquals(expected, actual_EventSet);
    }

    @Test
    public void execute_getStatus_DONE_TASK() {
        Set<Object> expected = logParser.execute("get status for event = \"DONE_TASK\"");
        Set<String> statusInString = new HashSet<>();
        statusInString.add("FAILED");
        statusInString.add("OK");
        actual_StatusSet = statusInString.stream().
                map(Converters::statusConverter).
                collect(Collectors.toSet());
        Assert.assertEquals(expected, actual_StatusSet);
    }

    @Test
    public void execute_getStatus() {
        Set<Object> expected = logParser.execute("get status");
        actual_StatusSet = getStatusSet();
        Assert.assertEquals(expected, actual_StatusSet);
    }
}