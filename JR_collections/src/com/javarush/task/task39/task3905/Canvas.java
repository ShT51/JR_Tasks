package com.javarush.task.task39.task3905;

public class Canvas {
    private Color[][] image;
    private int y;
    private int x;
    private Color desiredColor;
    private Color oldColor;
    private int max_X;
    private int max_Y;

    public Canvas(Color[][] image, int y, int x, Color desiredColor, Color oldColor) {
        this.image = image;
        this.y = y;
        this.x = x;
        this.desiredColor = desiredColor;
        this.oldColor = oldColor;
        this.max_X = image[0].length;
        this.max_Y = image.length;
    }

    public Color[][] getImage() {
        return image;
    }

    public void printMask(int x, int y) {
        print_plus_X(x);
        print_plus_Y(y);
        print_min_X(x);
        print_min_Y(y);
        print_min_XY(x, y);
        print_plus_XY(x, y);
    }

    private void print_min_X(int x) {
        while (x > 0) {
            --x;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_plus_X(int x) {
        while (x < max_X) {
            x++;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_min_Y(int y) {
        while (y > 0) {
            --y;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_plus_Y(int y) {
        while (y < max_Y) {
            y++;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_min_XY(int x, int y) {
        while (y > 0 && x > 0) {
            --y;
            --x;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

    private void print_plus_XY(int x, int y) {
        while (y < max_Y && x < max_X) {
            y++;
            x++;
            if (image[y][x] == oldColor) {
                image[y][x] = desiredColor;
            } else {
                break;
            }
        }
    }

}
