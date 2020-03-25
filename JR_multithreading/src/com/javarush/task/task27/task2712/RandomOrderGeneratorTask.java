package com.javarush.task.task27.task2712;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private final int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        if (tablets.size() > 0) {
            while (!Thread.interrupted()) {
                try {
                    int tabletNumber = ThreadLocalRandom.current().nextInt(0, tablets.size());
                    tablets.get(tabletNumber).createTestOrder();
                    Thread.sleep(interval);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
    }
}
