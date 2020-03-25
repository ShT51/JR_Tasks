package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager {

    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

        List<Advertisement> videos = new ArrayList<>();

        for (Advertisement video : storage.list()) {
            if (video.getHits() > 0) {
                videos.add(video);
            }
        }

        videos.sort(Comparator.comparing(Advertisement::getDuration));
        videos.sort(Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying));
        Collections.reverse(videos);

        List<Advertisement> optimalVideoSet = new ArrayList<>();
        int availableTime = timeSeconds;
        int currentDuration = 0;
        int currentVideo = 0;

        while (currentVideo < videos.size() && currentDuration <= availableTime) {

            Advertisement adv = videos.get(currentVideo);
            if (currentDuration + adv.getDuration() <= availableTime) {
                currentDuration += adv.getDuration();
                optimalVideoSet.add(adv);
            }
            currentVideo++;
        }

        /*
        while (currentVideo < videos.size() && currentDuration <= availableTime) {

            if (currentDuration + videos.get(currentVideo).getDuration() <= availableTime) {
                currentDuration += videos.get(currentVideo).getDuration();
                optimalVideoSet.add(videos.get(currentVideo));
                videos.get(currentVideo).revalidate();
            }
            currentVideo++;
        }
         */

        int amount = 0;
        int totalDuration = 0;

        for (Advertisement adv : optimalVideoSet) {
            totalDuration += adv.getDuration();
            amount += adv.getAmountPerOneDisplaying();
        }

        VideoSelectedEventDataRow videoSelectEvent = new VideoSelectedEventDataRow(optimalVideoSet,amount, totalDuration);
        StatisticManager manager = StatisticManager.getInstance();
        manager.register(videoSelectEvent);

        Comparator<Advertisement> comparator = Comparator.comparing(Advertisement::getAmountPerOneDisplaying).reversed();
        comparator = comparator.thenComparing(vid -> vid.getAmountPerOneDisplaying() / vid.getDuration() * 1000);
        optimalVideoSet.sort(comparator);

        for (Advertisement vid : optimalVideoSet) {
            vid.revalidate();
            System.out.println(vid.getName() + " is displaying... " + vid.getAmountPerOneDisplaying() + ", " + vid.getAmountPerOneDisplaying() * 1000 / vid.getDuration());
        }

    }
}

