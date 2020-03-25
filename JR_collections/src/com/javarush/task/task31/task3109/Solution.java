package com.javarush.task.task31.task3109;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* 
Читаем конфиги
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\3101\\inside1");
        Path dir = Paths.get("C:\\3101\\inside1");
        List<Path> pathsList = new ArrayList<>();

        pathsList = Files.walk(dir).collect(Collectors.toList());


        try (FileOutputStream fO = new FileOutputStream("C:\\3101\\archive.zip");
             ZipOutputStream zipOut = new ZipOutputStream(fO)) {

            for (Path p : pathsList) {

                zipOut.putNextEntry(new ZipEntry("archive."));
                Files.copy(file.toPath(), zipOut);
            }
        }
    }
}