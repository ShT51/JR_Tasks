package com.javarush.task.task34.task3412;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/* 
Добавление логирования в класс
*/

public class Solution {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);

    private int value1;
    private String value2;
    private Date value3;

    public Solution(int value1, String value2, Date value3) {
        logger.debug("Solution constructor starts with:" +
                "value1 = " + value1 +
                "value2 = " + value2 +
                "value3 = " + value3);
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static void main(String[] args) throws IOException {
    }

    public void calculateAndSetValue3(long value) {
        logger.trace("calculateAndSetValue3() starts with arg = " + value);
        value -= 133;
        if (value > Integer.MAX_VALUE) {
            // DEBUG - 2
            value1 = (int) (value / Integer.MAX_VALUE);
            logger.debug("in calculateAndSetValue3() value1 = " + value1);
        } else {
            // DEBUG - 3
            value1 = (int) value;
            logger.debug("in calculateAndSetValue3() value1 = " + value1);
        }
    }

    public void printString() {
        // TRACE 3
        logger.trace("printString() start. Value2 = " + value2);
        if (value2 != null) {
            System.out.println(value2.length());
        }
    }

    public void printDateAsLong() {
        // TRACE 2
        logger.trace("printDateAsLong() start. Value3 = " + value3);
        if (value3 != null) {
            System.out.println(value3.getTime());
        }
    }

    public void divide(int number1, int number2) {
        // TRACE 1
        logger.trace("divide() start. number1 = " + number1 + ", number2 = " + number2);
        try {
            System.out.println(number1 / number2);
        } catch (ArithmeticException e) {
            // ERROR 1
            logger.error("Some error", e);
        }
    }

    // DEBUG - 4
    public void setValue1(int value1) {
        this.value1 = value1;
        logger.debug("setValue1 = " + value1);
    }

    // DEBUG - 5
    public void setValue2(String value2) {
        this.value2 = value2;
        logger.debug("setValue2 = " + value2);
    }

    // DEBUG - 6
    public void setValue3(Date value3) {
        this.value3 = value3;
        logger.debug("setValue3 = " + value3);
    }
}
