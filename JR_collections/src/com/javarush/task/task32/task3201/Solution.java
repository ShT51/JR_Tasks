package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            long fileLeght = raf.length();

            if (fileLeght <= (long) number) {
                raf.seek(fileLeght);
                raf.write(text.getBytes());
            } else {
                raf.seek(number);
                raf.write(text.getBytes());
            }
        }
    }
}
