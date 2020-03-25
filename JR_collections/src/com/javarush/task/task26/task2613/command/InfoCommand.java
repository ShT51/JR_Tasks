package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;

class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() {
        Collection<CurrencyManipulator> allManipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        int totalSum = 0;

        ConsoleHelper.writeMessage(res.getString("before"));

        if (allManipulators.isEmpty()) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
            return;
        }

        for (CurrencyManipulator manipulator : allManipulators) {
            totalSum += manipulator.getTotalAmount();
        }

        if (totalSum == 0) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
            return;
        }

        for (CurrencyManipulator manipulator : allManipulators) {
            if (manipulator.hasMoney()) {
                int amount = manipulator.getTotalAmount();
                String currencyCode = manipulator.getCurrencyCode();
                ConsoleHelper.writeMessage(currencyCode + " - " + amount);
            }
        }
    }
}
