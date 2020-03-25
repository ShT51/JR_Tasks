package com.javarush.task.task22.task2213;

import javax.swing.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {

    private final int width;
    private final int height;
    private int[][] matrix;

    public Test(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }

    public static void main(String[] args) {
        Test test = new Test(20, 10);
        test.fillField();
        test.fillOneLine();
        test.printField();
        System.out.println("after remove");
        test.removeWithArray();
        test.printField();

    }

    public void removeWithArray() {
        ArrayList<int[]> listOfLines = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            int count = 0;
            for (int x = 0; x < width; x++) {
                int index = matrix[y][x];
                count += index;
            }
            if (count != width) {
                listOfLines.add(matrix[y]);
            }
        }
        while (listOfLines.size() < matrix.length) {
            listOfLines.add(0, new int[width]);
        }
            listOfLines.toArray(matrix);
    }

    public void fillField() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = 0;
                matrix[y][x] = index;
            }
        }
    }

    public void fillOneLine() {

        /*for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = (int) (Math.random() * 2);
            }
        } */

        for (int x = 0; x < width; x++) {
            matrix[7][x] = 1;
        }

        matrix[5][10] = 1;
        matrix[5][11] = 1;
        matrix[5][12] = 1;
        matrix[6][11] = 1;
    }

    public void printField() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = matrix[y][x];
                if (index == 1) {
                    System.out.print(" X ");
                } else if (index == 0) {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }
}
