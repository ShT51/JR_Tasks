package com.javarush.task.task35.task3507;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MyClassLoader2 extends ClassLoader {
    public Class<?> load(Path path, String packageName) {
        try {
            String className = packageName + "." + path.getFileName().toString().replace(".class", "");
            byte[] b = Files.readAllBytes(path);
            return defineClass(className, b, 0, b.length); //here main magic
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}