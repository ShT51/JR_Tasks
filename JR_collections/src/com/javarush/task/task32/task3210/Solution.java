package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];

        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");

        int textLengtb = text.length();
        long fileLenght = raf.length();

        byte[] buffer = new byte[textLengtb];

        raf.seek(number);

        raf.read(buffer, 0, textLengtb);

        String result = new String(buffer, Charset.defaultCharset());

        raf.seek(fileLenght);

        String tr = "true";
        String fl = "false";

        if (result.equals(text)) {
            raf.write(tr.getBytes());
        } else {
            raf.write(fl.getBytes());
        }
        raf.close();
    }
}
