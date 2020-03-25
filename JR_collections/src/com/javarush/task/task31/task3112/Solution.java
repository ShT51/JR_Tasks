package com.javarush.task.task31.task3112;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("C:\\outsideFolder\\MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method
        URL url = new URL(urlString);

        InputStream inputStream = url.openStream();
        Path tempFile = Files.createTempFile("temp-", ".tmp");
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();

        Path targetFile = pathFromURL(urlString, downloadDirectory);
        Files.move(tempFile, targetFile, StandardCopyOption.REPLACE_EXISTING);

        return targetFile;
    }

    public static Path pathFromURL (String urlString, Path downloadDirectory) {
        Pattern pt = Pattern.compile("\\w+\\.\\w+$");
        Matcher m = pt.matcher(urlString);
        m.find();
        String fileName = m.group();
        return Paths.get(downloadDirectory.toString(), fileName);
    }
}
