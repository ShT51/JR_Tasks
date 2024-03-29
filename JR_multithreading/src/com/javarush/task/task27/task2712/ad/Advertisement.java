package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        // начальная сумма, стоимость рекламы в копейках
        this.initialAmount = initialAmount;
        // кол-во показов
        this.hits = hits;
        // длительность одного олика
        this.duration = duration;
        // стоимость одного показа рекламного ролика в копейках
        amountPerOneDisplaying = hits > 0 ? initialAmount / hits : 0;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public int getHits() {
        return hits;
    }

    public void revalidate() {
        if (hits <= 0) {
            throw new NoVideoAvailableException();
        }
        hits--;
    }

}
