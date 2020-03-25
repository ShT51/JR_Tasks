package com.javarush.task.task39.task3905;

/* 
Залей меня полностью
*/

public class Solution {
    public static void main(String[] args) {
        Color[][] mask = new Color[10][8];
        mask[3][3] = Color.RED;
        mask[3][4] = Color.RED;
        mask[3][5] = Color.RED;
        mask[4][3] = Color.RED;
        mask[4][4] = Color.RED;
        mask[4][5] = Color.RED;
        mask[5][3] = Color.RED;
        mask[5][4] = Color.RED;
        mask[5][5] = Color.RED;

        printPicture(mask);

        PhotoPaint photoPaint = new PhotoPaint();
        System.out.println(photoPaint.paintFill(mask, 4, 4, Color.BLUE));

        printPicture(mask);
    }

    private static void printPicture(Color[][] mask) {
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                System.out.print(mask[i][j] + " ");
            }
            System.out.println();
        }
    }
}
