package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// сделать утилитный класс ?
// попытаться уйти от сохранения логов в память, решать через стримы

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    private final Pattern PT = Pattern.compile("(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s*(?<user>[a-zA-Z ]+)\\s*(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s*(?<event>[\\w]+)\\s?((?<task>[\\d]+)|)\\s*(?<status>[\\w]+)");
    private LogProducer logProducer;
    public Parser parser;
    private RequestHandler requestHandler;

    public LogParser(Path logDir) {
        this.logDir = logDir;
        logProducer = new LogProducer(logDir, PT);
        parser = new Parser(PT);
        requestHandler = new RequestHandler();
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        return parser.ipStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, user, "user");
        return parser.ipStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, event, "event");
        return parser.ipStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, status, "status");
        return parser.ipStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllUsers() {
        Stream<String> stream = logProducer.linesStream(null, null);
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        return parser.nameStream(stream).collect(Collectors.toSet()).size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, user, "user");
        return parser.filtredStream(stream, null, "event").
                collect(Collectors.toSet()).size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, ip, "ip");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.LOGIN, "event");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.DOWNLOAD_PLUGIN, "event");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.WRITE_MESSAGE, "event");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.SOLVE_TASK, "event");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.SOLVE_TASK, "event");
        stream = parser.filtredStream(stream, task, "task");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.DONE_TASK, "event");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Event.DONE_TASK, "event");
        stream = parser.filtredStream(stream, task, "task");
        return parser.nameStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, user, "user");
        stream = parser.filtredStream(stream, event, "event");
        return parser.dateStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Status.FAILED, "status");
        return parser.dateStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, Status.ERROR, "status");
        return parser.dateStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Optional<Date> optionalDate;
        Date date;

        Set<Date> dates = getDatesForUserAndEvent(user, Event.LOGIN, after, before);
        optionalDate = dates.stream().sorted().findFirst();
        date = optionalDate.orElse(null);
        return date;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {

        Stream<String> stream = logProducer.linesStream(after, before);
        Optional<Date> optionalDate;
        Date date;

        stream = parser.filtredStream(stream, user, "user");
        stream = parser.filtredStream(stream, Event.SOLVE_TASK, "event");
        stream = parser.filtredStream(stream, task, "task");
        optionalDate = parser.dateStream(stream).sorted().findFirst();
        date = optionalDate.orElse(null);
        return date;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        Optional<Date> optionalDate;
        Date date;

        stream = parser.filtredStream(stream, user, "user");
        stream = parser.filtredStream(stream, Event.DONE_TASK, "event");
        stream = parser.filtredStream(stream, task, "task");
        optionalDate = parser.dateStream(stream).sorted().findFirst();
        date = optionalDate.orElse(null);
        return date;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesForUserAndEvent(user, Event.WRITE_MESSAGE, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesForUserAndEvent(user, Event.DOWNLOAD_PLUGIN, after, before);
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        return parser.eventStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);

        stream = parser.filtredStream(stream, ip, "ip");
        return parser.eventStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        stream = parser.filtredStream(stream, user, "user");
        return parser.eventStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        stream = parser.filtredStream(stream, Status.FAILED, "status");
        return parser.eventStream(stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        stream = parser.filtredStream(stream, Status.ERROR, "status");
        return parser.eventStream(stream).collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        stream = parser.filtredStream(stream, task, "task");
        return (int) parser.filtredStream(stream, Event.SOLVE_TASK, "event").count();

    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        Stream<String> stream = logProducer.linesStream(after, before);
        stream = parser.filtredStream(stream, task, "task");
        return (int) parser.filtredStream(stream, Event.DONE_TASK, "event").count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> result = new HashMap<>();
        int key;
        int value;

        Stream<String> stream = logProducer.linesStream(after, before);
        Set<String> allTasks = parser.taskStream(stream).collect(Collectors.toSet());

        for (String task : allTasks) {
            key = Integer.parseInt(task);
            value = getNumberOfAttemptToSolveTask(key, after, before);
            if (value > 0) {
                result.put(key, value);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> result = new HashMap<>();
        Integer key;
        Integer value;

        Stream<String> stream = logProducer.linesStream(after, before);
        Set<String> allTasks = parser.taskStream(stream).collect(Collectors.toSet());

        for (String task : allTasks) {
            key = Integer.parseInt(task);
            value = getNumberOfSuccessfulAttemptToSolveTask(key, after, before);
            if (value > 0) {
                result.put(key, value);
            }
        }
        return result;
    }

    @Override
    public Set<Object> execute(String query) {
        requestHandler.getTags(query);
        String field1 = requestHandler.getField1();
        String field2 = requestHandler.getField2();
        String value1 = requestHandler.getValue1();
        Date after = requestHandler.getAfter();
        Date before = requestHandler.getBefore();

        Stream<String> stream = logProducer.linesStream(after, before);

        if (field2 != null || value1 != null) {
            stream = parser.filtredStream(stream, value1, field2);
        }
        switch (field1) {
            case ("user"):
                return parser.nameStream(stream).collect(Collectors.toSet());
            case ("ip"):
                return parser.ipStream(stream).collect(Collectors.toSet());
            case ("date"):
                return parser.dateStream(stream).collect(Collectors.toSet());
            case ("event"):
                return parser.eventStream(stream).collect(Collectors.toSet());
            case ("status"):
                return parser.statusStream(stream).collect(Collectors.toSet());
            default:
                return new HashSet<>();
        }
    }
}
