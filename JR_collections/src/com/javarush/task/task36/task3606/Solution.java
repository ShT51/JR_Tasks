package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File dir = null;
        try {
            dir = new File(URLDecoder.decode(this.packageName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File[] fileList = dir.listFiles();
        Class clazz;

        for (File file : fileList) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                clazz = new MyClassLoader().loadClazz(file.toPath());

                for (Class intf : clazz.getInterfaces()) {
                    if (intf.getSimpleName().equals("HiddenClass")) {
                        hiddenClasses.add(clazz);
                    }
                }
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        HiddenClass instance = null;

        for (Class clazz : hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                try {
                    Constructor<HiddenClass> constr = clazz.getDeclaredConstructor();
                    constr.setAccessible(true);
                    instance = constr.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }
}

