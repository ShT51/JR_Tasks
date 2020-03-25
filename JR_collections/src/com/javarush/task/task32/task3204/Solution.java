package com.javarush.task.task32.task3204;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

/*
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            bos.write(getPass(3));
            return bos;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static byte[] getPass(int passLength) {
        byte[] passArray = new byte[passLength];
        String pass;
        byte[] result = new byte[passLength];
        for (int i = 0; i < passArray.length; i++) {
            int symbolType = (int) (Math.random() * 3);
            //int symbolType = 0;
            switch (symbolType) {
                case (0):
                    passArray[i] = generateSymbol(48, 57);
                    break;
                case (1):
                    passArray[i] = generateSymbol(65, 90);
                    break;
                case (2):
                    passArray[i] = generateSymbol(97, 122);
                    break;
            }
        }
        pass = new String(passArray);
        while (!pass.contains("9")){
          result = getPass(passLength);
        }
        return result;
    }

    public static byte generateSymbol(int a, int b) {
        return (byte) (Math.random() * (b - a + 1) + a);
    }
}