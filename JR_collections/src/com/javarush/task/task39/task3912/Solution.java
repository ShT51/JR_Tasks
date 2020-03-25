package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Solution {
    private static int[][] square;

    public static void main(String[] args) {

        produceSquare(10, 10);
        printSquare();
        System.out.println("---------------");
        fillSquare(5, 5);
        printSquare();
        System.out.println(maxSquare(square));
    }

    public static int maxSquare(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int side = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i * j == 0)
                    continue;
                if (matrix[i][j] == 1)
                    matrix[i][j] = Math.min(matrix[i][j - 1], Math.min(matrix[i - 1][j], matrix[i - 1][j - 1])) + 1;

                if (matrix[i][j] > side)
                    side = matrix[i][j];
            }
        }

        return side * side;
    }

    public static void produceSquare(int y, int x) {
        square = new int[y][x];
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[0].length; j++) {
                square[i][j] = 0;
            }
        }
    }

    public static void fillSquare(int y, int x) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                square[i][j] = 1;
            }
        }
    }

    public static void printSquare() {
        for (int y = 0; y < square.length; y++) {
            for (int x = 0; x < square[0].length; x++) {
                System.out.print(square[y][x] + " ");
            }
            System.out.println();
        }
    }
}
