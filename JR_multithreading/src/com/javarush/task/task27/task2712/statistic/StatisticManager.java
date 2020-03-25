package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {
    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();
//    private Set<Cook> cooks = new HashSet<>();

    private StatisticManager() {
    }

    public synchronized static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }
        return instance;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    /*public void register(Cook cook) {
        cooks.add(cook);
    }

    public Set<Cook> getCooks() {
        return cooks;
    }*/

    public Map<Date, Double> getAdvertisementStatistic() {

        Map<Date, Double> result = new TreeMap<>(Collections.reverseOrder());

        List<EventDataRow> advertisementList = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        Date date;
        double amount;
        Calendar calendar;

        for (EventDataRow event : advertisementList) {
            VideoSelectedEventDataRow video = (VideoSelectedEventDataRow) event;
            amount = (double) video.getAmount() / 100.00;

            calendar = Calendar.getInstance();
            calendar.setTime(video.getDate());

            GregorianCalendar gc = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            date = gc.getTime();

            if (result.containsKey(date)) {
                amount += result.get(date);
            }
            result.put(date, amount);
        }

        return result;
    }

    public Map<Date, Map<String, Long>> getCookWorkLoadingStatistic() {

        Map<Date, Map<String, Long>> result = new TreeMap<>(Collections.reverseOrder());
        Map<String, Long> cookingTimePerEachCook = new TreeMap<>();

        List<EventDataRow> cookEventList = statisticStorage.getStorage().get(EventType.COOKED_ORDER);

        Date date;
        long cookingTime;
        String cookName;
        Calendar calendar;

        for (EventDataRow event : cookEventList) {
            CookedOrderEventDataRow cookEvent = (CookedOrderEventDataRow) event;
            cookName = cookEvent.getCookName();
            cookingTime = cookEvent.getTime() / 60;
            calendar = Calendar.getInstance();

            calendar.setTime(cookEvent.getDate());

            GregorianCalendar gc = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            date = gc.getTime();


            if (result.containsKey(date)) {
                if (cookingTimePerEachCook.containsKey(cookName)) {
                    cookingTime += cookingTimePerEachCook.get(cookName);
                }
            }
            cookingTimePerEachCook.put(cookName, cookingTime);
            result.put(date, cookingTimePerEachCook);
        }

        return result;
    }


    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType event : EventType.values()) {
                storage.put(event, new ArrayList<EventDataRow>());
            }
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        private void put(EventDataRow data) {
            EventType type = data.getType();
            for (Map.Entry<EventType, List<EventDataRow>> events : storage.entrySet()) {
                EventType eventType = events.getKey();
                List<EventDataRow> eventDataRow = events.getValue();
                if (type == eventType) {
                    eventDataRow.add(data);
                }
            }
        }
    }
}
