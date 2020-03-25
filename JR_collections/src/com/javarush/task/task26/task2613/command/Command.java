package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

/**
 * классы, наследующие интерфейс реализуют только один метод execute,
 * в котором инкапсулируют все операции с Получателем
 */
interface Command {
    void execute() throws InterruptOperationException;
}
