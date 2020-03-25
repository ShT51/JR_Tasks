package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        String curCode = ConsoleHelper.askCurrencyCode();
        String[] twoDigits = ConsoleHelper.getValidTwoDigits(curCode);
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        try {
            int note = Integer.parseInt(twoDigits[0]);
            int count = Integer.parseInt(twoDigits[1]);
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), note * count, curCode));
            currencyManipulator.addAmount(note, count);
        } catch (NumberFormatException nex) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
    }
}
