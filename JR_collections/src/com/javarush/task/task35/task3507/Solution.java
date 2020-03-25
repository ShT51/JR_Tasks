package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }


    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) throws UnsupportedEncodingException {
        Set<Animal> set = new HashSet<>();

        File dir = new File(URLDecoder.decode(pathToAnimals, "UTF-8"));
        File[] list = dir.listFiles();
        for (File file : list) {
            if (file.isFile() && file.getName().endsWith(".class")) {

                Class clazz = new MyClassLoader().loadClazz(file.toPath()); //Loading class from path

                int score = 0;
                //find interface Animal
                Class[] interfaces = clazz.getInterfaces();
                for (Class interf : interfaces)
                    if (interf.getSimpleName().equals("Animal")) {
                        score++;
                        break;
                    }

                //Find default constuctor
                Constructor[] constructors = clazz.getConstructors();
                for (Constructor constructor : constructors)
                    if (constructor.getParameterCount() == 0) {
                        score++;
                    }

                //if all ok, add to set
                if (score == 2)
                    try {
                        Animal animal = (Animal) clazz.getDeclaredConstructor().newInstance();
                        set.add(animal);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
            }
        }
        return set;
    }
}