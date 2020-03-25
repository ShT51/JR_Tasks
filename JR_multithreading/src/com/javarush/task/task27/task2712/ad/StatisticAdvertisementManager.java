package com.javarush.task.task27.task2712.ad;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage advertisementStorage;

    private StatisticAdvertisementManager() {
    }

    public synchronized static StatisticAdvertisementManager getInstance() {
        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }
        return instance;
    }

    public Map<String, Integer> getAvailableVideo() {

        return AdvertisementStorage.getInstance().list().stream()
                .filter(adv -> adv.getHits() > 0)
                .collect(Collectors.toMap(
                        k -> (String) k.getName(),
                        k -> (Integer) k.getHits()));
    }

    public List<String> getUnavailableVideo() {

        return AdvertisementStorage.getInstance().list().stream()
                .filter(adv -> adv.getHits() <= 0)
                .map(Advertisement::getName)
                .collect(Collectors.toList());
    }

}
