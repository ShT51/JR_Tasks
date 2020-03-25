package com.javarush.task.task34.task3413;

/* 
Кеш на основании SoftReference
*/

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class Solution {
    public static void main(String[] args) {
        SoftCache cache = new SoftCache();

        for (long i = 0; i < 2_500_000; i++) {
            cache.put(i, new AnyObject(i, null, null));
        }

        System.out.println();

        for (long i = 2_400_000; i < 2_500_000; i++) {
            System.out.println(cache.get(i));
        }

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        System.out.println(memoryBean.getHeapMemoryUsage().getUsed()/1024);
    }
}