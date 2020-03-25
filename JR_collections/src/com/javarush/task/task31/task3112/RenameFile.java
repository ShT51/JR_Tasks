package com.javarush.task.task31.task3112;

import java.io.File;
import java.nio.file.*;

public class RenameFile {

    public static void main(String[] args) {
    }

    public static void renameFiles(Path folder, String source, String destination) {
        //Path folder = Paths.get("C:\\Temp");

        File original = folder.resolve(source).toFile();
        File newFile = folder.resolve(destination).toFile();

        if (original.exists() & original.isFile() & original.canWrite()) {
            original.renameTo(newFile);
        }
    }
}
