package com.javarush.task.task31.task3101;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        List<Path> foundLines;
        MyFileVisitor myFileVisitor = new MyFileVisitor();

        File sourceFile = new File(args[1]);
        sourceFile.createNewFile();
        File destinationFile = new File(sourceFile.getParent() + "/allFilesContent.txt");

        if (FileUtils.isExist(destinationFile)) {
            FileUtils.deleteFile(destinationFile);
        }
        FileUtils.renameFile(sourceFile, destinationFile);

        Files.walkFileTree(Paths.get(args[0]), myFileVisitor);

        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {

            foundLines = myFileVisitor.getFoundLines();
            Collections.sort(foundLines, new FileNameComparator());

            for (Path file : foundLines) {
                FileInputStream fileInputStream = new FileInputStream(file.toFile());
                while (fileInputStream.available() > 0) {
                    fileOutputStream.write(fileInputStream.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                fileOutputStream.flush();
                fileInputStream.close();
            }
        }
    }
}
