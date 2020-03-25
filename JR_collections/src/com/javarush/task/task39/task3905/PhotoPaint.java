package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        int max_Y = image.length;
        int max_X = image[0].length;

        if (x >= max_X || y >= max_Y || image[y][x] == desiredColor) {
            return false;
        }
        Color oldColor = image[y][x];

        Canvas canvas = new Canvas(image, y, x, desiredColor, oldColor);

        canvas.printMask(x, y);

        image = canvas.getImage();
        image[y][x] = desiredColor;
/*
            while (x > 0) {
                x--;
                if (image[y][x] == oldColor) {
                    image[y][x] = desiredColor;
                } else {
                    break;
                }
            }

            while (y < max_Y) {
                if (image[y][x] == oldColor) {
                    image[y][x] = desiredColor;
                    y++;
                } else {
                    break;
                }
            }

            while (x < max_X) {
                if (image[y][x] == oldColor) {
                    image[y][x] = desiredColor;
                    x++;
                } else {
                    break;
                }
            }*/
        return true;
    }

    /*private void print_min_Y(Color[][] image, int y, int x, Color desiredColor, Color oldColor) {
        while (y > 0) {
            y--;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_min_X(Color[][] image, int y, int x, Color desiredColor, Color oldColor) {
        while (x > 0) {
            x--;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_min(Color[][] image, int y, int x, Color desiredColor, Color oldColor) {
        while (x > 0 && y > 0) {
            x--;
            y--;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }*/
}
