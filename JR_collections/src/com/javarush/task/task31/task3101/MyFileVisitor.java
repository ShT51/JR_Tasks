package com.javarush.task.task31.task3101;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private List<Path> foundLines = new ArrayList<>();

    public List<Path> getFoundLines() {
        return foundLines;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file);

        if (content.length <= 50 && !file.getFileName().toString().equals("allFilesContent.txt") && file.getFileName().toString().endsWith(".txt")) {
            foundLines.add(file);
        }

        return FileVisitResult.CONTINUE;
    }
}
