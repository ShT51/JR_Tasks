package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        Map<Date, Double> result = StatisticManager.getInstance().getAdvertisementStatistic();
        double totalProfit = 0;
        double profit = 0;

        for (Map.Entry<Date, Double> adv : result.entrySet()) {
            profit = adv.getValue();

            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,
                    "%1$te-%1$tb-%1$tY - %2$.2f",
                    adv.getKey(), profit));

            totalProfit += profit;
        }

        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", totalProfit));
    }

    public void printCookWorkloading() {
        Map<Date, Map<String, Long>> result = StatisticManager.getInstance().getCookWorkLoadingStatistic();

        for (Map.Entry<Date, Map<String, Long>> cookWork : result.entrySet()) {
            Date date = cookWork.getKey();
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%1$te-%1$tb-%1$tY", date));

            for (Map.Entry<String, Long> cook : cookWork.getValue().entrySet()) {
                ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %d min",
                        cook.getKey(), cook.getValue()));
            }
        }
    }

    public void printActiveVideoSet() {
        StatisticAdvertisementManager st = StatisticAdvertisementManager.getInstance();

        Map<String, Integer> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        result.putAll(st.getAvailableVideo());

        result.forEach((k,v)-> System.out.println(k + " - " + v));
    }

    public void printArchivedVideoSet() {
        StatisticAdvertisementManager.getInstance().getUnavailableVideo().stream()
                .sorted(String::compareToIgnoreCase)
                .forEach(System.out::println);

    }
}
