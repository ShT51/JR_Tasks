package com.javarush.task.task31.task3101;

import java.nio.file.Path;
import java.util.Comparator;

public class FileNameComparator implements Comparator<Path> {
    @Override
    public int compare(Path first, Path second) {
        return first.getFileName().compareTo(second.getFileName());
    }
}
