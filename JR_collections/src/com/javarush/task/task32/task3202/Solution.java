package com.javarush.task.task32.task3202;

import java.io.*;

/* 
Читаем из потока
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter writer = getAllDataFromInputStream(new FileInputStream("D:\\Yandex Disk\\YandexDisk\\Coding\\Compilation\\Java\\JavaRush\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task32\\task3202\\description.txt"));
        System.out.println(writer.toString());
    }

    public static StringWriter getAllDataFromInputStream(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            StringWriter sW = new StringWriter();
            while ((line = br.readLine()) != null) {
                sW.write(line);
            }
            return sW;
        } catch (Exception ex) {
            StringWriter sW = new StringWriter();
            sW.write("uppps..");
            return sW;
        }
    }
}