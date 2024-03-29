package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.FemaleFactory;
import com.javarush.task.task37.task3702.male.MaleFactory;

public class FactoryProducer {

    public static enum HumanFactoryType {
        MALE,
        FEMALE
    }

    public static AbstractFactory getFactory(HumanFactoryType factory) {
        switch (factory) {
            case MALE:
                return new MaleFactory();

            case FEMALE:
                return new FemaleFactory();

            default:
                throw new IllegalArgumentException("no transgender here!");
        }
    }
}
