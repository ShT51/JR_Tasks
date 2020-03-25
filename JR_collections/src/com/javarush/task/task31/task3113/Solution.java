package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/* 
Что внутри папки?
*/
public class Solution {
    private static Path directory;

    public static Path getDirectory() {
        return directory;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            directory = Paths.get(reader.readLine());
            if (!Files.isDirectory(directory)) {
                System.out.println(directory.toString() + " - не папка");
                return;
            }
        }
        MyFileVisitor myFileVisitor = new MyFileVisitor();
        Files.walkFileTree(directory, myFileVisitor);
        long allFilesSize = myFileVisitor.getAllFilesSize();
        long fileCount = myFileVisitor.getFileCount();
        long directoryCount = myFileVisitor.getDirectoryCount();

        System.out.printf("Всего папок - %d" + "\n" + "Всего файлов - %d" + "\n" + "Общий размер - %d", directoryCount, fileCount, allFilesSize);
    }
}
