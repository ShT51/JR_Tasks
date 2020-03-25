package com.javarush.task.task35.task3507;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MyClassLoader extends ClassLoader {

    public Class<?> loadClazz(Path path) {
        try {
            Class clazz;
            byte[] buffer = Files.readAllBytes(path);
            clazz = defineClass(null, buffer, 0, buffer.length);
            return clazz;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("some problems with I/O");
        }
        return null;
    }
}
