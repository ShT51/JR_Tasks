package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

/**
 * класс - Клиент
 * создает объекты команд, выбирая код операции
 */

public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        Operation operation;
        boolean userLogin = false;

        do {
            try {
                if (!userLogin) {
                    CommandExecutor.execute(Operation.LOGIN);
                    userLogin = true;
                }
                // узнаем код операции
                operation = ConsoleHelper.askOperation();
                // в соответствии с кодом, при помощи класса CommandExecutor - выбираем нужную Объект-команду
                // и выполняем метод execute
                CommandExecutor.execute(operation);
            } catch (InterruptOperationException ex) {
                ConsoleHelper.printExitMessage();
                break;
            }
        } while (!operation.equals(Operation.EXIT));
    }
}
