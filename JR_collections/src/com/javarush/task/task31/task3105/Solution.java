package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Map<ZipEntry, ByteArrayOutputStream> zipMap = new HashMap<>();
        File file = new File(args[0]);
        String fileName = file.getName();
        int size = 0;


       try (FileInputStream fI = new FileInputStream(args[1]);
             ZipInputStream zInput = new ZipInputStream(fI)) {
            ZipEntry ze;

            while ((ze = zInput.getNextEntry()) != null) {
                ByteArrayOutputStream byArOut = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while ((size = zInput.read(buffer)) != -1) {
                    byArOut.write(buffer, 0, size);
                    zipMap.put(ze, byArOut);
                }
            }
        }

        try (FileOutputStream fO = new FileOutputStream(args[1]);
             ZipOutputStream zOutput = new ZipOutputStream(fO)) {


            zOutput.putNextEntry(new ZipEntry("new" + File.separator + fileName));
            Files.copy(Paths.get(args[0]), zOutput);

            for (HashMap.Entry<ZipEntry, ByteArrayOutputStream> pair : zipMap.entrySet()) {
                if (!pair.getKey().getName().endsWith(fileName)) {
                    zOutput.putNextEntry(new ZipEntry(pair.getKey().getName()));
                    zOutput.write(pair.getValue().toByteArray());
                }
            }
        }

        for (Map.Entry<ZipEntry, ByteArrayOutputStream> pair : zipMap.entrySet()) {
            System.out.println(pair.getKey().getName() + " : " + pair.getValue().toString());

        }

    }
}

