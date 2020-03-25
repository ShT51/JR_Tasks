package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;
    private Set<Path> foundFiles = new HashSet<>();

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Set<Path> getFoundFiles() {
        return this.foundFiles;
    }

    public String getPartOfName() {
        return this.partOfName;
    }

    public String getPartOfContent() {
        return this.partOfContent;
    }

    public int getMinSize() {
        return this.minSize;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean partOfNameOK = false;
        boolean partOfContentOK = false;
        boolean minSizeOK = false;
        boolean maxSizeOK = false;

        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        String lines = new String(Files.readAllBytes(file));

        if (this.maxSize != 0 && content.length < this.maxSize) {
            maxSizeOK = true;
        }

        if (this.minSize != 0 && content.length > this.minSize) {
            minSizeOK = true;
        }

        if (this.partOfName != null && file.getFileName().toString().contains(this.partOfName)) {
            partOfNameOK = true;
        }

        if (this.partOfContent != null && lines.contains(this.partOfContent)) {
            partOfContentOK = true;
        }

        if (this.partOfName != null && this.partOfContent != null && this.minSize != 0 && this.maxSize != 0) {
            if (partOfContentOK && partOfNameOK && minSizeOK && maxSizeOK) {
                foundFiles.add(file);
            }
        }


            return FileVisitResult.CONTINUE;
        }
    }
