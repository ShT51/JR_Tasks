package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        List<String> fileList;
        String resultFileName = args[0];
        List<FileInputStream> fileInputStreamList = new ArrayList<>();

        fileList = Arrays.stream(args).sorted().limit(args.length-1).collect(Collectors.toList());

        fileList.forEach(System.out::println);

        for (String f : fileList) {
            try {
                fileInputStreamList.add(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                e.getCause();
            }
        }

        try (SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(fileInputStreamList));
             ZipInputStream zipInStream = new ZipInputStream(sequenceInputStream);
             FileOutputStream fileOutStream = new FileOutputStream(resultFileName)) {
            byte[] buf = new byte[1024 * 1024];

            while (zipInStream.getNextEntry() != null) {
                int count;
                while ((count = zipInStream.read(buf)) != -1) {
                    fileOutStream.write(buf, 0, count);
                }
            }
        }
    }
}
