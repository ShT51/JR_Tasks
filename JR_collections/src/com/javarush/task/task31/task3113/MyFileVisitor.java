package com.javarush.task.task31.task3113;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    private long directoryCount;
    private long fileCount;
    private long allFilesSize;

    public long getAllFilesSize() {
        return allFilesSize;
    }

    public long getDirectoryCount() {
        return directoryCount;
    }

    public long getFileCount() {
        return fileCount;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attr) throws IOException {
        if (!path.equals(Solution.getDirectory())) {
            directoryCount++;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attr) throws IOException {
        if (Files.isRegularFile(path)) {
            fileCount++;
            allFilesSize += Files.size(path);
        }
        return FileVisitResult.CONTINUE;
    }
}
