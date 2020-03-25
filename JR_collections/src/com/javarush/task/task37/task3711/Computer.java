package com.javarush.task.task37.task3711;

public class Computer {
    private CPU cpu = new CPU();
    private HardDrive hdd = new HardDrive();
    private Memory ram = new Memory();

    public void run() {
        cpu.calculate();
        ram.allocate();
        hdd.storeData();
    }

}
