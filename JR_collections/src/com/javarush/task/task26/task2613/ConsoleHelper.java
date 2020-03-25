package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String message = "";
        try {
            message = bis.readLine();
            if (message.toLowerCase().contains("exit")) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation") + "\n"
                + res.getString("operation.INFO") + "\n"
                + res.getString("operation.DEPOSIT") + "\n"
                + res.getString("operation.WITHDRAW") + "\n"
                + res.getString("operation.EXIT"));
        Operation operation;
        while (true) {
            String message = readString();
            try {
                int operationCode = Integer.parseInt(message);
                operation = Operation.getAllowableOperationByOrdinal(operationCode);
                break;
            } catch (Exception ex) {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return operation;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String currencyCode = "";
        while (true) {
            currencyCode = readString();
            if (currencyCode.length() == 3) {
                break;
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return currencyCode.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), "USD"));
        String message;
        String[] twoDigits;

        while (true) {
            message = readString();
            //message = currencyCode;
            if (message == null || !message.matches("\\d+\\s\\d+")) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            twoDigits = message.split("\\s");

            if (twoDigits.length != 2) {
                writeMessage(res.getString("invalid.data"));
            } else {
                break;
            }
        }
        return twoDigits;
    }

    public static void printExitMessage() {
        writeMessage("Thank you for visiting JavaRush cash machine. Good luck.");
    }
}
