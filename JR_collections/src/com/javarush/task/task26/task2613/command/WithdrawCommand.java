package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.TreeMap;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        String message;
        int moneyAmount;
        TreeMap<Integer, Integer> withdrawMoney = new TreeMap<>(Comparator.reverseOrder());

        ConsoleHelper.writeMessage(res.getString("before"));
        // 1. Считать код валюты
        String curCode = ConsoleHelper.askCurrencyCode();
        // 2. Получить манипулятор для этой валюты
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        // 3. Пока пользователь не введет корректные данные выполнять:
        while (true) {
            // 3.1 Попросить ввести сумму
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            message = ConsoleHelper.readString();
            // 3.2 Если введены некорректные данные, то сообщить об этом пользователю и вернуться к п. 3.1
            if (message == null || !message.matches("[1-9]\\d*")) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            // 3.3 Проверить, достаточно ли средств на счету
            moneyAmount = Integer.parseInt(message);
            // если денег не достаточно
            if (!currencyManipulator.isAmountAvailable(moneyAmount)) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            try {
                // выдаем пользователю нужную сумму наименьшим числом купюр
                withdrawMoney.putAll(currencyManipulator.withdrawAmount(moneyAmount));
                withdrawMoney.forEach((k, v) -> ConsoleHelper.writeMessage(String.format(res.getString("success.format"), v*k, curCode )));
                break;
                // если требуемую сумму имеющимся количеством купюр не выдать - кидаем исключение
            } catch (NotEnoughMoneyException ex) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
    }
}
